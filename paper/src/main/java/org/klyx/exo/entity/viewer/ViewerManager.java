package org.klyx.exo.entity.viewer;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.UnmodifiableView;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.events.ViewerHideEntityEvent;
import org.klyx.exo.entity.events.ViewerShowEntityEvent;
import org.klyx.exo.event.Event;
import org.klyx.exo.event.EventBus;
import org.klyx.exo.event.EventSubscription;
import org.klyx.exo.util.packet.impl.Packets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

public class ViewerManager {

    protected final ExoEntity entity;
    protected final Set<UUID> viewers = ConcurrentHashMap.newKeySet();
    // viewers that do not have the entity loaded
    protected final Set<UUID> unloadedViewers = ConcurrentHashMap.newKeySet();

    private final Set<UUID> explicitViewers = ConcurrentHashMap.newKeySet();
    private volatile boolean restrictedToExplicitViewers;
    private final List<Predicate<Player>> dynamicRules = new CopyOnWriteArrayList<>();
    private final List<EventSubscription> triggerSubscriptions = new CopyOnWriteArrayList<>();

    public ViewerManager(ExoEntity entity, List<Predicate<Player>> rules, List<UUID> initialViewers) {
        this.entity = entity;
        this.dynamicRules.addAll(rules);
        this.explicitViewers.addAll(initialViewers);
        this.restrictedToExplicitViewers = !initialViewers.isEmpty();
    }

    public @UnmodifiableView Set<UUID> getViewers() {
        Set<UUID> snapshot = new HashSet<>(this.viewers.size() + this.unloadedViewers.size());
        snapshot.addAll(this.viewers);
        snapshot.addAll(this.unloadedViewers);
        return Collections.unmodifiableSet(snapshot);
    }

    public @UnmodifiableView Set<UUID> getActiveViewers() {
        return Collections.unmodifiableSet(this.viewers);
    }

    public @UnmodifiableView Set<UUID> getUnloadedViewers() {
        return Collections.unmodifiableSet(this.unloadedViewers);
    }

    public @UnmodifiableView Set<UUID> getExplicitViewers() {
        return Collections.unmodifiableSet(this.explicitViewers);
    }

    public boolean isRestrictedToExplicitViewers() {
        return this.restrictedToExplicitViewers;
    }

    public boolean isViewer(UUID uuid) {
        return this.viewers.contains(uuid) || this.unloadedViewers.contains(uuid);
    }

    public int getViewerCount() {
        return this.viewers.size() + this.unloadedViewers.size();
    }

    public void addExplicitViewer(UUID playerUUID) {
        boolean becameRestricted = !this.restrictedToExplicitViewers;
        this.restrictedToExplicitViewers = true;
        boolean added = this.explicitViewers.add(playerUUID);

        if (becameRestricted) {
            syncViewers();
            return;
        }

        if (!added) return;
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) updateViewer(player);
    }

    public void removeExplicitViewer(UUID playerUUID) {
        this.explicitViewers.remove(playerUUID);
        removeViewer(playerUUID, false);
    }

    public void clearExplicitViewers() {
        if (!this.restrictedToExplicitViewers) return;

        this.explicitViewers.clear();
        this.restrictedToExplicitViewers = false;
        syncViewers();
    }

    public void addViewer(UUID playerUUID, boolean isChunkLoad) {
        if (viewers.contains(playerUUID)) return;

        if (!entity.isSpawned()) {
            this.unloadedViewers.add(playerUUID);
            return;
        }

        List<Packet<?>> defaultPackets = new ArrayList<>();
        defaultPackets.add(entity.getWorldStateManager().createSpawnPacket());
        defaultPackets.add(entity.entityMeta().createPacket(entity.entityId()));

        ViewerShowEntityEvent event = new ViewerShowEntityEvent(defaultPackets, playerUUID, isChunkLoad);
        this.entity.eventBus().post(event);

        this.unloadedViewers.remove(playerUUID);
        if (event.isCancelled()) {
            return;
        }

        this.viewers.add(playerUUID);
        Packets.INSTANCE.sendPackets(playerUUID, event.packets().toArray(Packet[]::new));
    }

    public void removeViewer(UUID playerUuid, boolean isChunkUnload) {
        boolean wasActive = this.viewers.contains(playerUuid);
        boolean wasUnloaded = this.unloadedViewers.contains(playerUuid);

        if (!wasActive && !wasUnloaded) return;

        if (!wasActive) {
            this.unloadedViewers.remove(playerUuid);
            return;
        }

        List<Packet<?>> defaultPackets = new ArrayList<>();
        defaultPackets.add(new ClientboundRemoveEntitiesPacket(this.entity.entityId()));

        ViewerHideEntityEvent event = new ViewerHideEntityEvent(defaultPackets, playerUuid, isChunkUnload);
        this.entity.eventBus().post(event);
        if (event.isCancelled()) return;
        this.viewers.remove(playerUuid);

        if (isChunkUnload) {
            this.unloadedViewers.add(playerUuid);
        } else {
            Packets.INSTANCE.sendPackets(playerUuid, event.packets().toArray(Packet[]::new));
        }
    }

    public void updateViewer(Player player) {
        if (!entity.isSpawned()) return;

        UUID playerUUID = player.getUniqueId();
        boolean isTracking = isViewer(playerUUID);

        if (shouldSee(player)) {
            boolean wasOffline = unloadedViewers.contains(playerUUID);
            addViewer(playerUUID, wasOffline);
        } else {
            if (isTracking) removeViewer(playerUUID, false);
        }
    }

    public ViewerRule addRule(Predicate<Player> rule) {
        dynamicRules.add(rule);
        syncViewers();

        return () -> {
            if (dynamicRules.remove(rule)) syncViewers();
        };
    }

    public <T extends Event> ViewerRule addViewersUpdateTrigger(EventBus bus, Class<T> eventClass, Function<T, Collection<Player>> playerExtractor) {
        EventSubscription sub = bus.subscribe(eventClass, event -> {
            Collection<Player> playersToUpdate = playerExtractor.apply(event);
            if (!playersToUpdate.isEmpty()) {
                playersToUpdate.forEach(this::updateViewer);
            }
        });

        triggerSubscriptions.add(sub);

        return () -> {
            sub.unsubscribe();
            this.triggerSubscriptions.remove(sub);
        };
    }

    private boolean shouldSee(Player player) {
        World userWorld = player.getWorld();
        World entityWorld = entity.getWorldStateManager().getWorldState().currentWorld();
        if (!userWorld.equals(entityWorld)) {
            return false;
        }

        if (restrictedToExplicitViewers && !explicitViewers.contains(player.getUniqueId())) {
            return false;
        }

        for (Predicate<Player> rule : dynamicRules) {
            try {
                if (!rule.test(player)) return false;
            } catch (Exception e) {
                Exo.logger().error("An error occurred while evaluating a viewer rule for {}: ", player.getName(), e);
                return false;
            }
        }

        return true;
    }

    public void registerAll() {
        syncViewers();
    }

    private void syncViewers() {
        if (!entity.isSpawned()) return;

        for (Player player : Bukkit.getOnlinePlayers()) {
            updateViewer(player);
        }
    }

    public void unregisterAll() {
        Set<UUID> viewers = new HashSet<>(this.viewers);
        viewers.addAll(this.unloadedViewers);

        for (UUID viewer : viewers) {
            this.removeViewer(viewer, false);
        }
    }

    void handleLoad(Player player) {
        updateViewer(player);
    }

    void handleUnload(Player player) {
        removeViewer(player.getUniqueId(), true);
    }

    public void sentPacketsToViewers(@Nullable Packet<?>... packets) {
        viewers.forEach(uuid -> Packets.INSTANCE.sendPackets(uuid, packets));
    }

    public void destroy() {
        this.viewers.clear();
        this.unloadedViewers.clear();

        for (EventSubscription sub : this.triggerSubscriptions) {
            sub.unsubscribe();
        }
        this.triggerSubscriptions.clear();
        this.dynamicRules.clear();
        this.explicitViewers.clear();
        this.restrictedToExplicitViewers = false;
    }

}
