package org.klyx.exo.utils;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftEntityType;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.klyx.exo.entities.AbstractEntity;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class PacketUtil {

    public static void sendPacket(@NotNull Player player, @NotNull Packet<?> packet) {
        ((CraftPlayer) player).getHandle().connection.connection.send(packet);
    }

    public static void sendPackets(@NotNull Player player, @NotNull Packet<?>... packets) {
        for (Packet<?> packet : packets) {
            sendPacket(player, packet);
        }
    }

    public static void sendPacket(@NotNull Collection<Player> players, @NotNull Packet<?> packet) {
        players.forEach(player -> PacketUtil.sendPacket(player, packet));
    }

    public static void sendBundledPackets(@NotNull Player player, @NotNull Packet<?>... packets) {
        Packet<? super ClientGamePacketListener>[] gamePackets = (Packet<? super ClientGamePacketListener>[]) packets;

        ClientboundBundlePacket bundlePacket = new ClientboundBundlePacket(List.of(gamePackets));
        sendPacket(player, bundlePacket);
    }

    public static @NotNull ClientboundAddEntityPacket createSpawnPacket(@NotNull AbstractEntity entity) {
        Location location = entity.getLocation();
        if (location == null) throw new IllegalArgumentException("Entity's location is null.");

        return new ClientboundAddEntityPacket(
                entity.getEntityId(),
                entity.getEntityUUID(),
                location.getX(), location.getY(), location.getZ(),
                location.getPitch(), location.getYaw(),
                CraftEntityType.bukkitToMinecraft(entity.getEntityType()),
                0,
                Vec3.ZERO,
                location.getYaw()
        );
    }

    public static @NotNull ClientboundSetPassengersPacket createPassengerPacket(@NotNull AbstractEntity entity) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        try {
            buffer.writeVarInt(entity.getEntityId());

            List<Integer> passengers = entity.getPassengers();
            int[] passengerArray = passengers.stream().mapToInt(Integer::intValue).toArray();
            buffer.writeVarIntArray(passengerArray);

            Constructor<ClientboundSetPassengersPacket> packet = ClientboundSetPassengersPacket.class.getDeclaredConstructor(FriendlyByteBuf.class);
            packet.setAccessible(true);

            return packet.newInstance(buffer);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while creating a passenger packet: " + e.getMessage());
        } finally {
            buffer.release();
        }

    }

}
