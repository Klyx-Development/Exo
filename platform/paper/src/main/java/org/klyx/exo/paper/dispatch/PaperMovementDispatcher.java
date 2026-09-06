package org.klyx.exo.paper.dispatch;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.phys.Vec3;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.ExoVec3d;
import org.klyx.exo.entity.data.world.EntityWorldState;
import org.klyx.exo.entity.dispatch.PacketCategory;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.common.MovementDispatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

public final class PaperMovementDispatcher implements MovementDispatcher {

    private static final Constructor<ClientboundRotateHeadPacket> ROTATE_HEAD_PACKET_CONSTRUCTOR;

    static {
        try {
            ROTATE_HEAD_PACKET_CONSTRUCTOR = ClientboundRotateHeadPacket.class.getDeclaredConstructor(FriendlyByteBuf.class);
            ROTATE_HEAD_PACKET_CONSTRUCTOR.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private final PacketDispatcher<Packet<?>> packetDispatcher;

    public PaperMovementDispatcher(PacketDispatcher<Packet<?>> packetDispatcher) {
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public void sendTeleport(ExoEntity entity, EntityWorldState state) {
        broadcast(entity, createTeleportPacket(entity, state));
    }

    @Override
    public void sendPosRot(ExoEntity entity, EntityWorldState state) {
        Vec3 deltaPos = toNmsVec3(state.deltaPosition());
        short xa = (short) (deltaPos.x() * 4096.0);
        short ya = (short) (deltaPos.y() * 4096.0);
        short za = (short) (deltaPos.z() * 4096.0);
        byte yRot = Mth.packDegrees(state.currentYaw());
        byte xRot = Mth.packDegrees(state.currentPitch());
        broadcast(entity, new ClientboundMoveEntityPacket.PosRot(entity.entityId(), xa, ya, za, yRot, xRot, state.currentOnGround()));
    }

    @Override
    public void sendPos(ExoEntity entity, EntityWorldState state) {
        Vec3 deltaPos = toNmsVec3(state.deltaPosition());
        short xa = (short) (deltaPos.x() * 4096.0);
        short ya = (short) (deltaPos.y() * 4096.0);
        short za = (short) (deltaPos.z() * 4096.0);
        broadcast(entity, new ClientboundMoveEntityPacket.Pos(entity.entityId(), xa, ya, za, state.currentOnGround()));
    }

    @Override
    public void sendRot(ExoEntity entity, EntityWorldState state) {
        byte yRot = Mth.packDegrees(state.currentYaw());
        byte xRot = Mth.packDegrees(state.currentPitch());
        broadcast(entity, new ClientboundMoveEntityPacket.Rot(entity.entityId(), yRot, xRot, state.currentOnGround()));
    }

    @Override
    public void sendHeadRot(ExoEntity entity, EntityWorldState state) {
        broadcast(entity, createRotateHeadPacket(entity, state));
    }

    @Override
    public void sendVelocity(ExoEntity entity, EntityWorldState state) {
        Vec3 velocity = state.velocity() != null ? toNmsVec3(state.velocity()) : Vec3.ZERO;
        broadcast(entity, new ClientboundSetEntityMotionPacket(entity.entityId(), velocity));
    }

    private ClientboundEntityPositionSyncPacket createTeleportPacket(ExoEntity entity, EntityWorldState state) {
        PositionMoveRotation pmr = new PositionMoveRotation(
                toNmsVec3(state.currentPos()), toNmsVec3(state.deltaPosition()), state.currentYaw(), state.currentPitch()
        );
        return new ClientboundEntityPositionSyncPacket(entity.entityId(), pmr, state.currentOnGround());
    }

    private ClientboundRotateHeadPacket createRotateHeadPacket(ExoEntity entity, EntityWorldState state) {
        FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
        try {
            byteBuf.writeVarInt(entity.entityId());
            byteBuf.writeByte(Mth.packDegrees(state.currentVerticalHeadRot()));
            return ROTATE_HEAD_PACKET_CONSTRUCTOR.newInstance(byteBuf);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to init packet", e);
        } finally {
            byteBuf.release();
        }
    }

    private void broadcast(ExoEntity entity, Packet<?> packet) {
        for (UUID viewer : entity.getActiveViewers()) {
            packetDispatcher.schedule(viewer, PacketCategory.DEFAULT, List.of(packet));
        }
    }

    private static Vec3 toNmsVec3(ExoVec3d vec) {
        return new Vec3(vec.x(), vec.y(), vec.z());
    }
}
