package org.klyx.exo.entity.data.world;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;
import org.bukkit.World;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.util.LocationHelper;

import java.lang.reflect.Constructor;

public class EntityWorldStateManager {

    private static final double BLOCK_THRESHOLD_SMALL = 0.0002;
    private static final float DEGREE_THRESHOLD = 0.0005f;
    private static final double BLOCK_THRESHOLD_BIG = 7.999755859375;

    private final ExoEntity entity;
    private EntityWorldState worldState;

    public EntityWorldStateManager(ExoEntity entity, Location initialLocation) {
        this(entity, initialLocation, null, 0f, false, requireWorld(initialLocation));
    }

    public EntityWorldStateManager(ExoEntity entity, Location initialLocation, @Nullable Vec3 initialVelocity,
                                   float initialVerticalHeadRot, boolean initialOnGround, World initialWorld) {
        this.entity = entity;
        this.worldState = new EntityWorldState(
                LocationHelper.toVector3d(initialLocation), null,
                initialLocation.getYaw(), null,
                initialLocation.getPitch(), null,
                initialVerticalHeadRot, null,
                initialOnGround, null,
                initialVelocity, null,
                initialWorld, null
        );
    }

    private static World requireWorld(Location location) {
        World world = location.getWorld();
        if (world == null) {
            throw new IllegalArgumentException("Location must have a non-null world to spawn an entity");
        }
        return world;
    }

    public EntityWorldState getWorldState() {
        return worldState;
    }

    public void teleport(Location location) {
        if (location.getWorld() != this.worldState.currentWorld()) {
            setWorld(location.getWorld());
        }

        updatePosition(LocationHelper.toVector3d(location), location.getYaw(), location.getPitch(),
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

    public void setVelocity(Vec3 velocity) {
        this.entity.sendPacketsToViewers(new ClientboundSetEntityMotionPacket(this.entity.entityId(), velocity));
        this.worldState = this.worldState.withVelocity(velocity);
    }

    public void setWorld(World newWorld) {
        World oldWorld = this.worldState.currentWorld();
        if (oldWorld.equals(newWorld)) return;

        boolean wasSpawned = this.entity.isSpawned();
        if (wasSpawned) {
            this.entity.getViewerManager().unregisterAll();
        }

        Vec3 pos = this.worldState.currentPos();
        Exo.entityManager().updateEntityWorld(this.entity, oldWorld, pos, newWorld, pos);
        this.worldState = this.worldState.withWorld(newWorld);

        if (!wasSpawned) return;
        this.entity.getViewerManager().registerAll();
    }

    public void lookAt(Vec3 target) {
        Vec3 pos = this.worldState.currentPos();
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

    private void updatePosition(Vec3 position, float yaw, float pitch,
                                float verticalHeadRot, boolean onGround, World world) {
        updatePosition(this.worldState.syncWith(position, yaw, pitch, verticalHeadRot, onGround, world));
    }

    private void updatePosition(EntityWorldState position) {
        Vec3 oldPos = this.worldState.currentPos();
        Vec3 newPos = position.currentPos();

        int oldChunkX = (int) Math.floor(oldPos.x()) >> 4;
        int oldChunkZ = (int) Math.floor(oldPos.z()) >> 4;

        int newChunkX = (int) Math.floor(newPos.x()) >> 4;
        int newChunkZ = (int) Math.floor(newPos.z()) >> 4;

        if (oldChunkX != newChunkX || oldChunkZ != newChunkZ) {
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

        if (this.worldState.needsFullSync()) {
            this.entity.sendPacketsToViewers(
                    createTeleportPacket(),
                    createRotateHeadPacket()
            );
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

        int entityId = this.entity.entityId();
        if (this.worldState.hasPositionChanged(BLOCK_THRESHOLD_BIG)) {
            this.entity.sendPacketsToViewers(createTeleportPacket());
        } else if (positionChange && pitchYawChange) {
            Vec3 deltaPos = this.worldState.deltaPosition();
            short xa = (short) (deltaPos.x() * 4096.0);
            short ya = (short) (deltaPos.y() * 4096.0);
            short za = (short) (deltaPos.z() * 4096.0);

            byte yRot = Mth.packDegrees(this.worldState.currentYaw());
            byte xRot = Mth.packDegrees(this.worldState.currentPitch());
            this.entity.sendPacketsToViewers(new ClientboundMoveEntityPacket.PosRot(
                    entityId,
                    xa, ya, za,
                    yRot, xRot,
                    this.worldState.currentOnGround()
            ));
        } else if (positionChange) {
            Vec3 deltaPos = this.worldState.deltaPosition();
            short xa = (short) (deltaPos.x() * 4096.0);
            short ya = (short) (deltaPos.y() * 4096.0);
            short za = (short) (deltaPos.z() * 4096.0);

            this.entity.sendPacketsToViewers(new ClientboundMoveEntityPacket.Pos(entityId,
                   xa, ya, za, this.worldState.currentOnGround()
            ));
        } else if (pitchYawChange) {
            byte yRot = Mth.packDegrees(this.worldState.currentYaw());
            byte xRot = Mth.packDegrees(this.worldState.currentPitch());
            this.entity.sendPacketsToViewers(new ClientboundMoveEntityPacket.Rot(entityId,
                    yRot, xRot, this.worldState.currentOnGround()
            ));
        }

        if (verticalHeadRotChange) {
            this.entity.sendPacketsToViewers(createRotateHeadPacket());
        }

        markSynced();
    }

    private ClientboundEntityPositionSyncPacket createTeleportPacket() {
        return new ClientboundEntityPositionSyncPacket(this.entity.entityId(), worldState.asPositionMoveRotation(), worldState.currentOnGround());
    }

    private ClientboundRotateHeadPacket createRotateHeadPacket() {
        try {
            FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
            byteBuf.writeVarInt(this.entity.entityId());
            byteBuf.writeByte(Mth.packDegrees((float) worldState.currentVerticalHeadRot()));

            Constructor<ClientboundRotateHeadPacket> constructor =
                    ClientboundRotateHeadPacket.class.getDeclaredConstructor(FriendlyByteBuf.class);

            constructor.setAccessible(true);
            return constructor.newInstance(byteBuf);
        } catch (Exception e) {
            throw new RuntimeException("Failed to reflectively instantiate packet", e);
        }
    }

    public void markSynced() {
        this.worldState = this.worldState.sync();
    }

    public ClientboundAddEntityPacket createSpawnPacket() {
        Vec3 velocity = this.worldState.velocity();
        return new ClientboundAddEntityPacket(
                this.entity.entityId(), this.entity.uuid(),
                worldState.currentPos().x(), worldState.currentPos().y(), worldState.currentPos().z(),
                worldState.currentYaw(), worldState.currentPitch(), this.entity.nmsEntityType(), this.entity.objectDataValue(),
                velocity != null ? velocity : Vec3.ZERO, worldState.currentVerticalHeadRot()
        );
    }

}
