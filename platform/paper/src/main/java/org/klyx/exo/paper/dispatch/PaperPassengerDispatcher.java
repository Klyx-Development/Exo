package org.klyx.exo.paper.dispatch;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.dispatch.PacketCategory;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.common.PassengerDispatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public final class PaperPassengerDispatcher implements PassengerDispatcher {

    private static final Constructor<ClientboundSetPassengersPacket> SET_PASSENGERS_PACKET_CONSTRUCTOR;

    static {
        try {
            SET_PASSENGERS_PACKET_CONSTRUCTOR = ClientboundSetPassengersPacket.class.getDeclaredConstructor(FriendlyByteBuf.class);
            SET_PASSENGERS_PACKET_CONSTRUCTOR.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private final PacketDispatcher<Packet<?>> packetDispatcher;

    public PaperPassengerDispatcher(PacketDispatcher<Packet<?>> packetDispatcher) {
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public void dispatchPassengers(ExoEntity viewerSource, int vehicleEntityId, Collection<Integer> passengerIds) {
        Packet<?> packet = createPassengerPacket(vehicleEntityId, passengerIds);
        for (UUID viewer : viewerSource.getActiveViewers()) {
            packetDispatcher.schedule(viewer, PacketCategory.DEFAULT, List.of(packet));
        }
    }

    public static ClientboundSetPassengersPacket createPassengerPacket(int entityId, Collection<Integer> passengers) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        try {
            buffer.writeVarInt(entityId);

            int[] passengerArray = passengers.stream().mapToInt(Integer::intValue).toArray();
            buffer.writeVarIntArray(passengerArray);

            return SET_PASSENGERS_PACKET_CONSTRUCTOR.newInstance(buffer);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("failed to init passenger packet: " + e.getMessage());
        } finally {
            buffer.release();
        }
    }
}
