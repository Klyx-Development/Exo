package org.klyx.exo.entity.data.world;

import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.ExoPos;
import org.klyx.exo.entity.ExoVec3d;
import org.klyx.exo.common.MovementDispatcher;
import org.klyx.exo.world.ExoWorld;

public class EntityWorldStateManager {

    private static final double BLOCK_THRESHOLD_SMALL = 0.0002;
    private static final float DEGREE_THRESHOLD = 0.0005f;
    private static final double BLOCK_THRESHOLD_BIG = 7.999755859375;

    private final ExoEntity entity;
    private EntityWorldState worldState;

    public EntityWorldStateManager(ExoEntity entity, ExoWorld world, ExoPos initialPos) {
        this(entity, world, initialPos, null, false);
    }

    public EntityWorldStateManager(ExoEntity entity, ExoWorld world, ExoPos initialPos,
                                   @Nullable ExoVec3d initialVelocity, boolean initialOnGround) {
        this.entity = entity;
        this.worldState = new EntityWorldState(
                initialPos.toVec3d(), null,
                initialPos.yaw(), null,
                initialPos.pitch(), null,
                0f, null,
                initialOnGround, null,
                initialVelocity, null,
                world, null
        );
    }

    public EntityWorldState getWorldState() {
        return worldState;
    }

    public void teleport(ExoWorld world, ExoPos pos) {
        if (!world.equals(this.worldState.currentWorld())) {
            setWorld(world);
        }

        updatePosition(pos.toVec3d(), pos.yaw(), pos.pitch(),
                this.worldState.currentVerticalHeadRot(), this.worldState.currentOnGround(),
                this.worldState.currentWorld());
    }

    public void setYaw(float yaw) {
        updatePosition(this.worldState.currentPos(), yaw, this.worldState.currentPitch(),
                yaw, this.worldState.currentOnGround(), this.worldState.currentWorld());
    }

    public void setPitch(float pitch) {
        updatePosition(this.worldState.currentPos(), this.worldState.currentYaw(), pitch,
                this.worldState.currentVerticalHeadRot(), this.worldState.currentOnGround(),
                this.worldState.currentWorld());
    }

    public void setVerticalHeadRot(float verticalHeadRot) {
        updatePosition(this.worldState.currentPos(), this.worldState.currentYaw(),
                this.worldState.currentPitch(), verticalHeadRot,
                this.worldState.currentOnGround(), this.worldState.currentWorld());
    }

    public void setOnGround(boolean onGround) {
        updatePosition(this.worldState.currentPos(), this.worldState.currentYaw(),
                this.worldState.currentPitch(), this.worldState.currentVerticalHeadRot(),
                onGround, this.worldState.currentWorld());
    }

    public void setVelocity(ExoVec3d velocity) {
        EntityWorldState updated = this.worldState.withVelocity(velocity);
        Exo.platform().movementDispatcher().sendVelocity(this.entity, updated);
        this.worldState = updated;
    }

    public void setWorld(ExoWorld newWorld) {
        ExoWorld oldWorld = this.worldState.currentWorld();
        if (oldWorld.equals(newWorld)) return;

        boolean wasSpawned = this.entity.isSpawned();
        if (wasSpawned) {
            this.entity.getViewerManager().unregisterAll();
        }

        ExoVec3d pos = this.worldState.currentPos();
        if (this.entity.isTracked()) {
            Exo.entityManager().updateEntityWorld(this.entity, oldWorld, pos, newWorld, pos);
        }
        this.worldState = this.worldState.withWorld(newWorld);

        if (!wasSpawned) return;
        this.entity.getViewerManager().registerAll();
    }

    public void lookAt(ExoVec3d target) {
        ExoVec3d pos = this.worldState.currentPos();
        double dx = target.x() - pos.x();
        double dy = target.y() - pos.y();
        double dz = target.z() - pos.z();
        double distanceXZ = Math.sqrt(dx * dx + dz * dz);

        float yaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
        float pitch = (float) Math.toDegrees(Math.atan2(-dy, distanceXZ));

        this.worldState = this.worldState.syncWith(
                this.worldState.currentPos(),
                yaw, pitch, yaw,
                this.worldState.currentOnGround(),
                this.worldState.currentWorld()
        );

        dispatchMovementUpdates();
    }

    private void updatePosition(ExoVec3d position, float yaw, float pitch,
                                float verticalHeadRot, boolean onGround, ExoWorld world) {
        updatePosition(this.worldState.syncWith(position, yaw, pitch, verticalHeadRot, onGround, world));
    }

    private void updatePosition(EntityWorldState position) {
        ExoVec3d oldPos = this.worldState.currentPos();
        ExoVec3d newPos = position.currentPos();

        int oldChunkX = (int) Math.floor(oldPos.x()) >> 4;
        int oldChunkZ = (int) Math.floor(oldPos.z()) >> 4;

        int newChunkX = (int) Math.floor(newPos.x()) >> 4;
        int newChunkZ = (int) Math.floor(newPos.z()) >> 4;

        if ((oldChunkX != newChunkX || oldChunkZ != newChunkZ) && this.entity.isTracked()) {
            Exo.entityManager().updateEntityChunk(this.entity, oldPos, newPos);
        }

        this.worldState = position;
        dispatchMovementUpdates();
    }

    private void dispatchMovementUpdates() {
        if (!this.entity.isSpawned() || this.entity.getViewerCount() == 0) {
            markSynced();
            return;
        }

        MovementDispatcher movementDispatcher = Exo.platform().movementDispatcher();

        if (this.worldState.needsFullSync()) {
            movementDispatcher.sendTeleport(this.entity, this.worldState);
            movementDispatcher.sendHeadRot(this.entity, this.worldState);
            markSynced();
            return;
        }

        boolean positionChange = this.worldState.hasPositionChanged(BLOCK_THRESHOLD_SMALL);
        boolean pitchYawChange = this.worldState.hasPitchYawChanged(DEGREE_THRESHOLD);
        boolean verticalHeadRotChange = this.worldState.hasVerticalHeadRotChanged(DEGREE_THRESHOLD);
        boolean groundChanged = this.worldState.hasOnGroundChanged();

        if (!positionChange && !pitchYawChange && !verticalHeadRotChange && !groundChanged) {
            return;
        }

        if (this.worldState.hasPositionChanged(BLOCK_THRESHOLD_BIG)) {
            movementDispatcher.sendTeleport(this.entity, this.worldState);
        } else if (positionChange && pitchYawChange) {
            movementDispatcher.sendPosRot(this.entity, this.worldState);
        } else if (positionChange) {
            movementDispatcher.sendPos(this.entity, this.worldState);
        } else if (pitchYawChange) {
            movementDispatcher.sendRot(this.entity, this.worldState);
        }

        if (verticalHeadRotChange) {
            movementDispatcher.sendHeadRot(this.entity, this.worldState);
        }

        markSynced();
    }

    public void markSynced() {
        this.worldState = this.worldState.sync();
    }

    /**
     * Used for manually updating the entity's world state.
     * This is for situations where you don't necessarily want to teleport the entity,
     * but you want to update it's next position when it's next loaded, etc.
     */
    public void updateState(EntityWorldState state) {
        this.worldState = state;
    }

}
