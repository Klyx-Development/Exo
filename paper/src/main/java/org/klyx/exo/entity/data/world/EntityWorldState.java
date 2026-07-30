package org.klyx.exo.entity.data.world;

import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;
import org.bukkit.World;
import org.jspecify.annotations.Nullable;

/**
 * Representation of the world state of an entity, used to determine whether a sync is needed.
 */
public record EntityWorldState(
        Vec3 currentPos, @Nullable Vec3 lastSyncedPos,
        float currentYaw, @Nullable Float lastSyncedYaw,
        float currentPitch, @Nullable Float lastSyncedPitch,
        float currentVerticalHeadRot, @Nullable Float lastSyncedVerticalHeadRot,
        boolean currentOnGround, @Nullable Boolean lastSyncedOnGround,
        @Nullable Vec3 currentVelocity, @Nullable Vec3 lastSyncedVelocity,
        World currentWorld, @Nullable World lastSyncedWorld
) {

    public @Nullable Vec3 velocity() {
        return this.currentVelocity;
    }

    public PositionMoveRotation asPositionMoveRotation() {
        return new PositionMoveRotation(currentPos, deltaPosition(), currentYaw, currentPitch);
    }

    public Location asLocation() {
        return new Location(currentWorld, currentPos.x(), currentPos.y(), currentPos.z(), currentYaw, currentPitch);
    }

    public EntityWorldState withVelocity(@Nullable Vec3 velocity) {
        return new EntityWorldState(
                this.currentPos, this.lastSyncedPos,
                this.currentYaw, this.lastSyncedYaw,
                this.currentPitch, this.lastSyncedPitch,
                this.currentVerticalHeadRot, this.lastSyncedVerticalHeadRot,
                this.currentOnGround, this.lastSyncedOnGround,
                velocity, this.lastSyncedVelocity,
                this.currentWorld, this.lastSyncedWorld
        );
    }

    public EntityWorldState withPosition(Vec3 position) {
        return new EntityWorldState(
                position, this.lastSyncedPos,
                this.currentYaw, this.lastSyncedYaw,
                this.currentPitch, this.lastSyncedPitch,
                this.currentVerticalHeadRot, this.lastSyncedVerticalHeadRot,
                this.currentOnGround, this.lastSyncedOnGround,
                this.currentVelocity, this.lastSyncedVelocity,
                this.currentWorld, this.lastSyncedWorld
        );
    }

    public EntityWorldState withYaw(float yaw) {
        return new EntityWorldState(
                this.currentPos, this.lastSyncedPos,
                yaw, this.lastSyncedYaw,
                this.currentPitch, this.lastSyncedPitch,
                this.currentVerticalHeadRot, this.lastSyncedVerticalHeadRot,
                this.currentOnGround, this.lastSyncedOnGround,
                this.currentVelocity, this.lastSyncedVelocity,
                this.currentWorld, this.lastSyncedWorld
        );
    }

    public EntityWorldState withPitch(float pitch) {
        return new EntityWorldState(
                this.currentPos, this.lastSyncedPos,
                this.currentYaw, this.lastSyncedYaw,
                pitch, this.lastSyncedPitch,
                this.currentVerticalHeadRot, this.lastSyncedVerticalHeadRot,
                this.currentOnGround, this.lastSyncedOnGround,
                this.currentVelocity, this.lastSyncedVelocity,
                this.currentWorld, this.lastSyncedWorld
        );
    }

    public EntityWorldState withVerticalHeadRot(float verticalHeadRot) {
        return new EntityWorldState(
                this.currentPos, this.lastSyncedPos,
                this.currentYaw, this.lastSyncedYaw,
                this.currentPitch, this.lastSyncedPitch,
                verticalHeadRot, this.lastSyncedVerticalHeadRot,
                this.currentOnGround, this.lastSyncedOnGround,
                this.currentVelocity, this.lastSyncedVelocity,
                this.currentWorld, this.lastSyncedWorld
        );
    }

    public EntityWorldState withOnGround(boolean onGround) {
        return new EntityWorldState(
                this.currentPos, this.lastSyncedPos,
                this.currentYaw, this.lastSyncedYaw,
                this.currentPitch, this.lastSyncedPitch,
                this.currentVerticalHeadRot, this.lastSyncedVerticalHeadRot,
                onGround, this.lastSyncedOnGround,
                this.currentVelocity, this.lastSyncedVelocity,
                this.currentWorld, this.lastSyncedWorld
        );
    }

    public EntityWorldState withWorld(World world) {
        return new EntityWorldState(
                this.currentPos, this.lastSyncedPos,
                this.currentYaw, this.lastSyncedYaw,
                this.currentPitch, this.lastSyncedPitch,
                this.currentVerticalHeadRot, this.lastSyncedVerticalHeadRot,
                this.currentOnGround, this.lastSyncedOnGround,
                this.currentVelocity, this.lastSyncedVelocity,
                world, this.lastSyncedWorld
        );
    }

    public boolean needsFullSync() {
        return this.lastSyncedPos == null
                || this.lastSyncedYaw == null
                || this.lastSyncedPitch == null
                || this.lastSyncedVerticalHeadRot == null
                || this.lastSyncedOnGround == null
                || this.lastSyncedWorld == null;
    }

    public EntityWorldState syncWith(Vec3 newPos, float newYaw, float newPitch,
                                     float newHeadRot, boolean newIsOnGround,
                                     World newWorld) {
        return new EntityWorldState(
                newPos, this.currentPos,
                newYaw, this.currentYaw,
                newPitch, this.currentPitch,
                newHeadRot, this.currentVerticalHeadRot,
                newIsOnGround, this.currentOnGround,
                this.currentVelocity, this.currentVelocity, // carry velocity through unchanged
                newWorld, this.currentWorld
        );
    }

    public EntityWorldState sync() {
        return new EntityWorldState(
                this.currentPos, this.currentPos,
                this.currentYaw, this.currentYaw,
                this.currentPitch, this.currentPitch,
                this.currentVerticalHeadRot, this.currentVerticalHeadRot,
                this.currentOnGround, this.currentOnGround,
                this.currentVelocity, this.currentVelocity,
                this.currentWorld, this.currentWorld
        );
    }

    public EntityWorldState syncPosition() {
        return new EntityWorldState(
                this.currentPos, this.currentPos,
                this.currentYaw, this.lastSyncedYaw,
                this.currentPitch, this.lastSyncedPitch,
                this.currentVerticalHeadRot, this.lastSyncedVerticalHeadRot,
                this.currentOnGround, this.lastSyncedOnGround,
                this.currentVelocity, this.lastSyncedVelocity,
                this.currentWorld, this.currentWorld
        );
    }

    public EntityWorldState syncYawPitch() {
        return new EntityWorldState(
                this.currentPos, this.lastSyncedPos,
                this.currentYaw, this.currentYaw,
                this.currentPitch, this.currentPitch,
                this.currentVerticalHeadRot, this.lastSyncedVerticalHeadRot,
                this.currentOnGround, this.lastSyncedOnGround,
                this.currentVelocity, this.lastSyncedVelocity,
                this.currentWorld, this.lastSyncedWorld
        );
    }

    public EntityWorldState syncVerticalHeadRot() {
        return new EntityWorldState(
                this.currentPos, this.lastSyncedPos,
                this.currentYaw, this.lastSyncedYaw,
                this.currentPitch, this.lastSyncedPitch,
                this.currentVerticalHeadRot, this.currentVerticalHeadRot,
                this.currentOnGround, this.lastSyncedOnGround,
                this.currentVelocity, this.lastSyncedVelocity,
                this.currentWorld, this.lastSyncedWorld
        );
    }

    public EntityWorldState syncOnGround() {
        return new EntityWorldState(
                this.currentPos, this.lastSyncedPos,
                this.currentYaw, this.lastSyncedYaw,
                this.currentPitch, this.lastSyncedPitch,
                this.currentVerticalHeadRot, this.lastSyncedVerticalHeadRot,
                this.currentOnGround, this.currentOnGround,
                this.currentVelocity, this.lastSyncedVelocity,
                this.currentWorld, this.lastSyncedWorld
        );
    }

    public EntityWorldState syncVelocity() {
        return new EntityWorldState(
                this.currentPos, this.lastSyncedPos,
                this.currentYaw, this.lastSyncedYaw,
                this.currentPitch, this.lastSyncedPitch,
                this.currentVerticalHeadRot, this.lastSyncedVerticalHeadRot,
                this.currentOnGround, this.lastSyncedOnGround,
                this.currentVelocity, this.currentVelocity,
                this.currentWorld, this.lastSyncedWorld
        );
    }

    public EntityWorldState syncWorld() {
        return new EntityWorldState(
                this.currentPos, this.lastSyncedPos,
                this.currentYaw, this.lastSyncedYaw,
                this.currentPitch, this.lastSyncedPitch,
                this.currentVerticalHeadRot, this.lastSyncedVerticalHeadRot,
                this.currentOnGround, this.lastSyncedOnGround,
                this.currentVelocity, this.lastSyncedVelocity,
                this.currentWorld, this.currentWorld
        );
    }

    public Vec3 deltaPosition() {
        if (this.lastSyncedPos == null) {
            return Vec3.ZERO;
        }
        return new Vec3(
                this.currentPos.x() - this.lastSyncedPos.x(),
                this.currentPos.y() - this.lastSyncedPos.y(),
                this.currentPos.z() - this.lastSyncedPos.z()
        );
    }

    public double distanceSqToLastSynced() {
        if (this.lastSyncedPos == null) return 0.0;
        double dx = this.currentPos.x() - this.lastSyncedPos.x();
        double dy = this.currentPos.y() - this.lastSyncedPos.y();
        double dz = this.currentPos.z() - this.lastSyncedPos.z();
        return dx * dx + dy * dy + dz * dz;
    }

    public boolean hasPositionChanged(double blockThreshold) {
        if (this.lastSyncedPos == null) return true;
        return distanceSqToLastSynced() > (blockThreshold * blockThreshold);
    }

    public boolean hasPitchYawChanged(float degreeThreshold) {
        if (this.lastSyncedYaw == null || this.lastSyncedPitch == null) return true;
        return Math.abs(this.currentYaw - this.lastSyncedYaw) > degreeThreshold
                || Math.abs(this.currentPitch - this.lastSyncedPitch) > degreeThreshold;
    }

    public boolean hasVerticalHeadRotChanged(float degreeThreshold) {
        if (this.lastSyncedVerticalHeadRot == null) return true;
        return Math.abs(this.currentVerticalHeadRot - this.lastSyncedVerticalHeadRot) > degreeThreshold;
    }

    public boolean hasOnGroundChanged() {
        if (this.lastSyncedOnGround == null) return true;
        return this.currentOnGround != this.lastSyncedOnGround;
    }

    public boolean hasVelocityChanged() {
        if (this.currentVelocity == null && this.lastSyncedVelocity == null) return false;
        if (this.currentVelocity == null || this.lastSyncedVelocity == null) return true;
        return !this.currentVelocity.equals(this.lastSyncedVelocity);
    }

    public boolean hasWorldChanged() {
        if (this.lastSyncedWorld == null) return true;
        return !this.currentWorld.equals(this.lastSyncedWorld);
    }
}
