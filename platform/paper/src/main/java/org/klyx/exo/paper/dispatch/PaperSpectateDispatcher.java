package org.klyx.exo.paper.dispatch;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import org.klyx.exo.common.SpectateDispatcher;
import org.klyx.exo.entity.dispatch.PacketCategory;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.player.ExoPlayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class PaperSpectateDispatcher implements SpectateDispatcher {

    private static final Constructor<ClientboundSetCameraPacket> SPECTATE_PACKET_CONSTRUCTOR;

    static {
        try {
            SPECTATE_PACKET_CONSTRUCTOR = ClientboundSetCameraPacket.class.getDeclaredConstructor(FriendlyByteBuf.class);
            SPECTATE_PACKET_CONSTRUCTOR.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private final PacketDispatcher<Packet<?>> packetDispatcher;

    public PaperSpectateDispatcher(PacketDispatcher<Packet<?>> packetDispatcher) {
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public void dispatchSpectate(ExoPlayer spectator, int entityIdToSpectate) {
        Packet<?> packet = createCameraPacket(entityIdToSpectate);
        packetDispatcher.schedule(spectator.uuid(), PacketCategory.DEFAULT, List.of(packet));
    }

    private ClientboundSetCameraPacket createCameraPacket(int entityId) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        try {
            buf.writeVarInt(entityId);
            return SPECTATE_PACKET_CONSTRUCTOR.newInstance(buf);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to init spectate packet", e);
        } finally {
            buf.release();
        }
    }
}
