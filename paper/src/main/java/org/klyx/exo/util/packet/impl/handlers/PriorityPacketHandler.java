package org.klyx.exo.util.packet.impl.handlers;

import io.papermc.paper.connection.PlayerCommonConnection;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.klyx.exo.util.packet.impl.PacketFunction;

import java.util.List;
import java.util.UUID;

public class PriorityPacketHandler<R, P extends Packet<?>> implements Comparable<PriorityPacketHandler<?, ?>> {

    private final UUID id = UUID.randomUUID();
    private final PacketFunction<R, P> function;
    private final Class<R> receiverType;
    private final Integer priority;

    public PriorityPacketHandler(PacketFunction<R, P> function, Class<R> receiverType, int priority) {
        this.function = function;
        this.receiverType = receiverType;
        this.priority = priority;
    }

    @SuppressWarnings("unchecked")
    public List<Packet<?>> handle(Connection connection, Packet<?> packet) {
        // Cast the packet and connection to the right types used by this method
        P castPacket;
        try {
            castPacket = (P) packet;
        } catch (ClassCastException e) {
            return List.of(packet);
        }

        R receiver;
        if (Player.class.isAssignableFrom(receiverType)) {
            if (connection.getPlayer() == null) {
                return List.of(packet);
            }
            receiver = (R) connection.getPlayer().getBukkitEntity();
        } else if (net.minecraft.world.entity.player.Player.class.isAssignableFrom(receiverType)) {
            if (connection.getPlayer() == null) {
                return List.of(packet);
            }
            receiver = (R) connection.getPlayer();
        } else if (PlayerCommonConnection.class.isAssignableFrom(receiverType)) {
            PlayerCommonConnection paperConnection = null;
            if (connection.getPacketListener() instanceof ServerConfigurationPacketListenerImpl serverConfigurationPacketListener) {
                paperConnection = serverConfigurationPacketListener.paperConnection;
            } else if (connection.getPacketListener() instanceof ServerGamePacketListenerImpl serverGamePacketListener) {
                paperConnection = serverGamePacketListener.paperConnection();
            }
            receiver = (R) paperConnection;
        } else {
            return List.of(packet);
        }

        if (receiver == null) {
            return List.of(packet);
        }

        return function.apply(receiver, castPacket);
    }

    @Override
    public int compareTo(@NonNull PriorityPacketHandler<?, ?> other) {
        return priority.compareTo(other.priority);
    }

    @Override
    public boolean equals(Object other) {
        return this == other || id == ((PriorityPacketHandler<?, ?>) other).getId();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public UUID getId() {
        return id;
    }
}
