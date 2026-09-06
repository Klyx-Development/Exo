package org.klyx.exo.paper.dispatch;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.dispatch.PacketCategory;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.common.LeashDispatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

public final class PaperLeashDispatcher implements LeashDispatcher {

    private static final Constructor<ClientboundSetEntityLinkPacket> SET_ENTITY_LINK_PACKET_CONSTRUCTOR;

    static {
        try {
            SET_ENTITY_LINK_PACKET_CONSTRUCTOR = ClientboundSetEntityLinkPacket.class.getDeclaredConstructor(FriendlyByteBuf.class);
            SET_ENTITY_LINK_PACKET_CONSTRUCTOR.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private final PacketDispatcher<Packet<?>> packetDispatcher;

    public PaperLeashDispatcher(PacketDispatcher<Packet<?>> packetDispatcher) {
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public void dispatchLink(ExoEntity entity, int holderEntityId) {
        Packet<?> packet = createLinkPacket(entity.entityId(), holderEntityId);
        for (UUID viewer : entity.getActiveViewers()) {
            packetDispatcher.schedule(viewer, PacketCategory.DEFAULT, List.of(packet));
        }
    }

    public static ClientboundSetEntityLinkPacket createLinkPacket(int sourceId, int destId) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        try {
            buffer.writeInt(sourceId);
            buffer.writeInt(destId);

            return SET_ENTITY_LINK_PACKET_CONSTRUCTOR.newInstance(buffer);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Something went wrong while creating an entity link packet: " + e.getMessage());
        } finally {
            buffer.release();
        }
    }
}
