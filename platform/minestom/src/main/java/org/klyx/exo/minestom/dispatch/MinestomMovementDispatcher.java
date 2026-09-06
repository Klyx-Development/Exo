package org.klyx.exo.minestom.dispatch;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.play.EntityHeadLookPacket;
import net.minestom.server.network.packet.server.play.EntityPositionAndRotationPacket;
import net.minestom.server.network.packet.server.play.EntityPositionPacket;
import net.minestom.server.network.packet.server.play.EntityPositionSyncPacket;
import net.minestom.server.network.packet.server.play.EntityRotationPacket;
import net.minestom.server.network.packet.server.play.EntityVelocityPacket;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.ExoVec3d;
import org.klyx.exo.entity.data.world.EntityWorldState;
import org.klyx.exo.entity.dispatch.PacketCategory;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.minestom.util.MinestomLocationHelper;
import org.klyx.exo.common.MovementDispatcher;

import java.util.List;
import java.util.UUID;

public final class MinestomMovementDispatcher implements MovementDispatcher {

    private final PacketDispatcher<ServerPacket> packetDispatcher;

    public MinestomMovementDispatcher(PacketDispatcher<ServerPacket> packetDispatcher) {
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public void sendTeleport(ExoEntity entity, EntityWorldState state) {
        Pos pos = MinestomLocationHelper.toPos(state.asExoPos());
        Vec delta = MinestomLocationHelper.toVec(state.deltaPosition());
        broadcast(entity, new EntityPositionSyncPacket(
                entity.entityId(), pos, delta, state.currentYaw(), state.currentPitch(), state.currentOnGround()));
    }

    @Override
    public void sendPosRot(ExoEntity entity, EntityWorldState state) {
        ExoVec3d delta = state.deltaPosition();
        broadcast(entity, new EntityPositionAndRotationPacket(
                entity.entityId(), packDelta(delta.x()), packDelta(delta.y()), packDelta(delta.z()),
                state.currentYaw(), state.currentPitch(), state.currentOnGround()));
    }

    @Override
    public void sendPos(ExoEntity entity, EntityWorldState state) {
        ExoVec3d delta = state.deltaPosition();
        broadcast(entity, new EntityPositionPacket(
                entity.entityId(), packDelta(delta.x()), packDelta(delta.y()), packDelta(delta.z()), state.currentOnGround()));
    }

    @Override
    public void sendRot(ExoEntity entity, EntityWorldState state) {
        broadcast(entity, new EntityRotationPacket(entity.entityId(), state.currentYaw(), state.currentPitch(), state.currentOnGround()));
    }

    @Override
    public void sendHeadRot(ExoEntity entity, EntityWorldState state) {
        broadcast(entity, new EntityHeadLookPacket(entity.entityId(), state.currentYaw()));
    }

    @Override
    public void sendVelocity(ExoEntity entity, EntityWorldState state) {
        Vec velocity = state.velocity() != null ? MinestomLocationHelper.toVec(state.velocity()) : Vec.ZERO;
        broadcast(entity, new EntityVelocityPacket(entity.entityId(), velocity));
    }

    private static short packDelta(double delta) {
        return (short) (delta * 4096.0);
    }

    private void broadcast(ExoEntity entity, ServerPacket packet) {
        for (UUID viewer : entity.getActiveViewers()) {
            packetDispatcher.schedule(viewer, PacketCategory.DEFAULT, List.of(packet));
        }
    }
}
