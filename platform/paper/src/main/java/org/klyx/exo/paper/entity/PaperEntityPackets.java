package org.klyx.exo.paper.entity;

import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.phys.Vec3;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.data.world.EntityWorldState;

public final class PaperEntityPackets {

    private PaperEntityPackets() {}

    public static ClientboundAddEntityPacket createSpawnPacket(ExoEntity entity) {
        EntityWorldState worldState = entity.getWorldStateManager().getWorldState();
        Vec3 velocity = worldState.velocity() != null
                ? new Vec3(worldState.velocity().x(), worldState.velocity().y(), worldState.velocity().z())
                : Vec3.ZERO;

        return new ClientboundAddEntityPacket(
                entity.entityId(), entity.uuid(),
                worldState.currentPos().x(), worldState.currentPos().y(), worldState.currentPos().z(),
                worldState.currentYaw(), worldState.currentPitch(),
                PaperEntityTypes.toNms(entity.entityType()), entity.objectDataValue(),
                velocity, worldState.currentVerticalHeadRot()
        );
    }
}
