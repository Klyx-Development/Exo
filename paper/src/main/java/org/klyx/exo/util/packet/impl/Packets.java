package org.klyx.exo.util.packet.impl;

import io.papermc.paper.adventure.PaperAdventure;
import io.papermc.paper.connection.DisconnectionReason;
import io.papermc.paper.connection.ReadablePlayerCookieConnectionImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.util.ThreadsafeMultimap;
import org.klyx.exo.util.packet.PacketHelper;
import org.klyx.exo.util.packet.impl.handlers.PacketHandlerUnregisterer;
import org.klyx.exo.util.packet.impl.handlers.PacketHandlers;
import org.klyx.exo.util.packet.impl.handlers.PlayerConnectionHandler;
import org.klyx.exo.util.packet.impl.handlers.PriorityPacketHandler;
import org.klyx.exo.util.packet.impl.listener.PacketHandler;
import org.klyx.exo.util.packet.impl.listener.PacketListener;
import xyz.bitsquidd.bits.log.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.StreamSupport;

public class Packets {

    public static final Packets INSTANCE = new Packets("klyx_packets");

    private final String key;

    public static final boolean KICK_ON_FAILURE = false;
    public static final int DEFAULT_HANDLER_PRIORITY = 100;

    public final Field connectionField;
    {
        try {
            connectionField = ReadablePlayerCookieConnectionImpl.class.getDeclaredField("connection");
            connectionField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private final Map<UUID, PlayerConnectionHandler> playerConnectionHandlers = new HashMap<>();
    private final Map<Class<?>, PacketHandlers<?>> packetHandlers = new ConcurrentHashMap<>();
    private final ThreadsafeMultimap<PacketListener, PacketHandlerUnregisterer> listenerUnregisterers = new ThreadsafeMultimap<>();

    private final AtomicInteger incomingPackets = new AtomicInteger(0);
    private final Queue<Integer> lastInPackets = new ConcurrentLinkedQueue<>();
    private final AtomicInteger outgoingPackets = new AtomicInteger(0);
    private final Queue<Integer> lastOutPackets = new ConcurrentLinkedQueue<>();

    private final Queue<Class<? extends Packet<?>>> incomingPacketsThisSecond = new ConcurrentLinkedQueue<>();
    private final Queue<Class<? extends Packet<?>>> outgoingPacketsThisSecond = new ConcurrentLinkedQueue<>();
    private int loggingPacketThreshold = -1;
    private boolean loggingEnabled = false;

    private boolean registered = false;

    public Packets(String key) {
        this.key = key;
        startTask();
    }

    public void startTask() {
        new BukkitRunnable() {
            int tick = 0;

            @Override
            public void run() {
                lastInPackets.add(incomingPackets.getAndSet(0));
                if (lastInPackets.size() > 20) lastInPackets.poll();

                lastOutPackets.add(outgoingPackets.getAndSet(0));
                if (lastOutPackets.size() > 20) lastOutPackets.poll();

                if (loggingEnabled && tick % 20 == 0) {
                    if (incomingPacketsThisSecond.size() + outgoingPacketsThisSecond.size() > loggingPacketThreshold) {
                        Logger.info("INCOMING: " + formatPacketTypeSet(incomingPacketsThisSecond));
                        Logger.info("OUTGOING: " + formatPacketTypeSet(outgoingPacketsThisSecond));
                    }
                    incomingPacketsThisSecond.clear();
                    outgoingPacketsThisSecond.clear();
                }

                tick++;
            }
        }.runTaskTimerAsynchronously(Exo.plugin(), 1, 1);
    }

    private String formatPacketTypeSet(Queue<Class<? extends Packet<?>>> packetTypes) {
        Map<Class<? extends Packet<?>>, Integer> typeCounts = new HashMap<>();

        for (Class<? extends Packet<?>> packetType : packetTypes) {
            typeCounts.put(packetType, typeCounts.getOrDefault(packetType, 0) + 1);
        }

        StringBuilder builder = new StringBuilder();
        builder.append("[");

        boolean first = true;
        List<Map.Entry<Class<? extends Packet<?>>, Integer>> entries = new ArrayList<>(typeCounts.entrySet());
        entries.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        for (Map.Entry<Class<? extends Packet<?>>, Integer> entry : entries) {
            if (!first) builder.append(", ");
            first = false;

            builder.append(entry.getKey().getSimpleName()).append(": ").append(entry.getValue());
        }

        builder.append("]");
        return builder.toString();
    }

    public void register() {
        if (registered) return;
        registered = true;

        Bukkit.getOnlinePlayers().forEach(this::registerPlayer);
    }

    public void unregister() {
        if (!registered) return;
        registered = false;

        for (UUID playerUUID : new HashSet<>(playerConnectionHandlers.keySet())) {
            unregisterPlayer(playerUUID, false);
        }
    }

    public <R, P extends Packet<?>> PacketHandlerUnregisterer registerHandler(
            Class<P> packetClass, Class<R> receiver, Integer priority, PacketFunction<R, P> function
    ) {
        PriorityPacketHandler<R, P> handler = new PriorityPacketHandler<>(function, receiver, priority);

        //noinspection unchecked
        PacketHandlers<P> handlers = (PacketHandlers<P>) packetHandlers.computeIfAbsent(packetClass, k -> new PacketHandlers<>(packetClass));
        handlers.assign(handler);
        return () -> handlers.unassign(handler);
    }

    public boolean hasHandlers(Packet<?> packet) {
        if (packet instanceof ClientboundBundlePacket bundlePacket) {
            return StreamSupport.stream(bundlePacket.subPackets().spliterator(), false)
                    .anyMatch(this::hasHandlers);
        }

        return packetHandlers.containsKey(packet.getClass());
    }

    @SuppressWarnings("unchecked")
    public List<Packet<?>> handlePacket(Connection connection, Packet<?> input, boolean incoming) {
        if (incoming) {
            incomingPackets.incrementAndGet();
            if (loggingEnabled) incomingPacketsThisSecond.add((Class<? extends Packet<?>>) input.getClass());
        } else {
            outgoingPackets.incrementAndGet();
            if (loggingEnabled) outgoingPacketsThisSecond.add((Class<? extends Packet<?>>) input.getClass());
        }

        // Early-exit if this type has no handlers!
        if (!hasHandlers(input)) return List.of(input);

        List<Packet<?>> packets = new LinkedList<>();
        List<Class<?>> pendingClasses = new LinkedList<>();
        Set<Class<?>> checkedClasses = new HashSet<>();

        // Start the list out as having only the current packet
        packets.add(input);
        pendingClasses.add(input.getClass());

        // Loop through all entries in the list with one type at a time
        // until we've processed all variants
        while (!pendingClasses.isEmpty()) {
            // Determine the type we're checking for in this iteration!
            Class<?> type = pendingClasses.removeFirst();
            checkedClasses.add(type);

            if (type == ClientboundBundlePacket.class) {
                // If the type is a bundle packet, unpack it!
                int index = 0;
                while (index < packets.size()) {
                    Packet<?> packet = packets.get(index);

                    if (packet instanceof ClientboundBundlePacket bundlePacket) {
                        packets.remove(packet);

                        int insertIndex = index;
                        for (@Nullable Packet<?> nested : bundlePacket.subPackets()) {
                            // Ignore null packets
                            if (nested == null) continue;

                            // Add the packet back to the original list but relative to the index of the bundle
                            packets.add(insertIndex++, nested);

                            // Ignore nested bundle packets as those are already handled by
                            // the iterator continuing down the list!
                            if (nested instanceof ClientboundBundlePacket) continue;

                            Class<?> nestedClass = nested.getClass();
                            if (hasHandlers(nested)) {
                                if (checkedClasses.contains(nestedClass)) {
                                    // If we got here there were two handlers creating each other's type, this would
                                    // cause infinite packet copies to be made, this should not happen!
                                    Logger.warn("Skipping packet processing for " + nestedClass.getName() + " to avoid infinite loop");
                                }

                                pendingClasses.add(nestedClass);
                            }
                        }
                    } else {
                        index++;
                    }
                }
            } else {
                // Otherwise, run each packet handler across the list! This supports handlers spliting
                // packets into two of the same type properly as we won't run the same handler many times
                // on the same packets.
                packetHandlers.get(type).read(handlers -> {
                    for (PriorityPacketHandler<?, ?> handler : handlers) {
                        // Automatically increment indices as we never want to process the same
                        // packet twice!
                        int index = 0;

                        while (index < packets.size()) {
                            Packet<?> packet = packets.get(index);

                            // Ignore packets not of the type being handled!
                            if (!type.isInstance(packet)) {
                                index++;
                                continue;
                            }

                            // Call the packet handler on this packet in the list
                            List<@Nullable Packet<?>> output;
                            try {
                                output = handler.handle(connection, packet);
                            } catch (Exception e) {
                                Logger.error("Error in packet handler", e);

                                if (KICK_ON_FAILURE) {
                                    // If an error occurs, kick the player on the main thread!
                                    Bukkit.getScheduler().callSyncMethod(
                                            Exo.plugin(), () -> {
                                                ((ServerCommonPacketListenerImpl) connection.getPacketListener()).disconnect(
                                                        PaperAdventure.asVanilla(Component.text("An error occurred while parsing packets", NamedTextColor.RED)),
                                                        DisconnectionReason.INVALID_PAYLOAD
                                                );
                                                return null;
                                            }
                                    );
                                }
                                continue;
                            }

                            // Perform faster checks that don't require re-insertion if the output is a singular
                            // packet of the same type or null.
                            if (output.size() == 1) {
                                Packet<?> firstOutput = output.getFirst();

                                if (firstOutput == packet) {
                                    // Proceed to the next index as normal, this handler was only
                                    // listening and changes nothing!
                                    index++;
                                    continue;
                                }

                                if (firstOutput == null) {
                                    // Remove the packet but check the same index again as it has shifted
                                    packets.remove(index);
                                    continue;
                                }

                                if (firstOutput.getClass() == type) {
                                    packets.set(index, firstOutput);
                                    index++;
                                    continue;
                                }
                            }

                            // Remove the packet itself and insert the new ones
                            // at the same index.
                            packets.remove(index);

                            int insertIndex = index;
                            for (Packet<?> nested : output) {
                                // Ignore null packets
                                if (nested == null) continue;

                                // If we insert anything at the index itself we continue to the next element!
                                // If the type is the same we don't want to end up in a loop, if the type is different
                                // we're not checking for it anyway so we don't care in this loop.
                                if (insertIndex == index) index++;

                                // Add the packet back to the original list but relative to the index of the packet
                                packets.add(insertIndex++, nested);

                                // Ignore nested same packets as those are already handled by
                                // the iterator continuing down the list!
                                if (type.isInstance(nested)) continue;

                                // Queue up to check this type if it has handlers and is not already checked!
                                Class<?> nestedClass = nested.getClass();
                                if (hasHandlers(nested)) {
                                    if (checkedClasses.contains(nestedClass)) {
                                        Logger.warn("Skipping packet processing for " + nestedClass.getName() + " to avoid infinite loop");
                                        continue;
                                    }

                                    pendingClasses.add(nestedClass);
                                }

                            }
                        }
                    }
                });
            }
        }

        return packets;
    }

    public void registerListener(PacketListener listener) {
        Class<?> clazz = listener.getClass();

        while (PacketListener.class.isAssignableFrom(clazz)) {
            for (Method method : clazz.getDeclaredMethods()) {
                method.setAccessible(true);

                if (!method.isAnnotationPresent(PacketHandler.class)) continue;
                PacketHandler annotation = method.getAnnotation(PacketHandler.class);

                // Method must satisfy the [PacketHandlerFunction] interface
                if (!((method.getParameterCount() == 2)
                        && (Player.class.isAssignableFrom(method.getParameterTypes()[0]) || net.minecraft.world.entity.player.Player.class.isAssignableFrom(method.getParameterTypes()[0]))
                        && (Packet.class.isAssignableFrom(method.getParameterTypes()[1]))
                        && (Packet.class.isAssignableFrom(method.getReturnType()) || List.class.isAssignableFrom(method.getReturnType()))
                )) {
                    throw new IllegalArgumentException(
                            "PacketHandler " + method + " on " + clazz + " doesn't match the PacketHandlerFunction interface (2 parameters, player and packet, returns packet or list of packets)"
                    );
                }

                boolean returnsList = List.class.isAssignableFrom(method.getReturnType());
                @SuppressWarnings("unchecked")
                PacketHandlerUnregisterer unregisterer =
                        registerHandler(
                                (Class<Packet<?>>) method.getParameterTypes()[1],
                                method.getParameterTypes()[0],
                                annotation.priority(),
                                (player, packet) -> {
                                    try {
                                        if (returnsList) {
                                            return (List<Packet<?>>) method.invoke(listener, player, packet);
                                        } else {
                                            Object result = method.invoke(listener, player, packet);

                                            if (result == null) {
                                                // Ignore result if returned value is null
                                                return Collections.emptyList();
                                            } else if (result instanceof ClientboundBundlePacket bundle) {
                                                // Turn bundles into a list of packets so they can be properly handled!
                                                List<Packet<?>> packets = new ArrayList<>();
                                                bundle.subPackets().forEach(packets::add);
                                                return packets;
                                            } else {
                                                // Wrap singular packets into a list so we can support both nicely
                                                return List.of((Packet<?>) result);
                                            }
                                        }
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        );

                // Store the unregisterer
                listenerUnregisterers.put(listener, unregisterer);
            }

            clazz = clazz.getSuperclass();
        }
    }

    public void unregisterListener(PacketListener listener) {
        listenerUnregisterers.removeAll(listener).forEach(PacketHandlerUnregisterer::unregister);
    }

    public void registerPlayer(Player player) {
        Connection connection = ((CraftPlayer) player).getHandle().connection.connection;
        registerConnection(player.getUniqueId(), connection);
    }

    public void registerConnection(UUID uuid, Connection connection) {
        PlayerConnectionHandler handler = new PlayerConnectionHandler(key, this, connection);
        PlayerConnectionHandler previous = playerConnectionHandlers.put(uuid, handler);
        if (previous != null) previous.unregister(false);
        handler.register();
    }

    public void unregisterPlayer(UUID uuid, boolean disconnect) {
        PlayerConnectionHandler handler = playerConnectionHandlers.remove(uuid);
        if (handler != null) handler.unregister(disconnect);
    }

    public Optional<Connection> getPlayerConnection(UUID uuid) {
        return Optional.ofNullable(playerConnectionHandlers.get(uuid)).map(PlayerConnectionHandler::getConnection);
    }

    public void sendPacket(Player player, Packet<?> packet) {
        Connection connection = ((CraftPlayer) player).getHandle().connection.connection;
        connection.send(packet);
    }

    public void sendPacket(UUID uuid, Packet<?> packet) {
        getPlayer(uuid).ifPresent(player -> sendPacket(player, packet));
    }

    public void sendPackets(Player player, Packet<?>... packets) {
        Connection connection = ((CraftPlayer) player).getHandle().connection.connection;
        sendPackets(connection, packets);
    }

    public void sendPackets(UUID uuid, Packet<?>... packets) {
        getPlayer(uuid).ifPresentOrElse(player -> sendPackets(player, packets), () -> Logger.warn("Player " + uuid + " is not online"));
    }

    public void sendPackets(Connection connection, Packet<?>... packets) {
        if (packets.length == 0) return;

        if (packets.length == 1) {
            connection.send(packets[0]);
        } else {
            PacketHelper.bundleMultiple(Arrays.asList(packets)).forEach(bundle -> sendPackets(connection, bundle));
        }
    }

    public int getInPacketsPerSecond() {
        return lastInPackets.stream().mapToInt(Integer::intValue).sum();
    }

    public int getOutPacketsPerSecond() {
        return lastOutPackets.stream().mapToInt(Integer::intValue).sum();
    }

    public Field getConnectionField() {
        return connectionField;
    }

    public static Optional<Player> getPlayer(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null || !player.isOnline()) return Optional.empty();

        return Optional.of(player);
    }

}
