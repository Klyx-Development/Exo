package org.klyx.exo.entities;

import net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import org.bukkit.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.data.metadata.EntityMetadata;
import org.klyx.exo.storage.EntityStorage;
import org.klyx.exo.utils.PacketUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractEntity {

    protected final @NotNull String name;
    protected final @NotNull UUID entityUUID;
    protected final @NotNull EntityType entityType;
    protected final int entityId;

    protected boolean spawned = false;

    private final @NotNull Set<Player> viewers = ConcurrentHashMap.newKeySet();
    private @Nullable Location location;

    private Set<Integer> passengers;
    // the entity that this entity is currently riding
    public int ridingEntityId = -1;

    public final @NotNull EntityMetadata entityMetadata;

    public AbstractEntity(@NotNull EntityType entityType) {
        this(EntityStorage.newEntityId(), UUID.randomUUID(), entityType);
    }

    public AbstractEntity(int entityId, @NotNull UUID entityUUID, @NotNull EntityType entityType) {
        this.entityId = entityId;
        this.entityUUID = entityUUID;
        this.entityType = entityType;

        this.name = "entity-" + entityUUID.toString().substring(0, 8);
        this.entityMetadata = new EntityMetadata(entityId);

        initDefaultMetadata();
    }

    protected void initDefaultMetadata() {
        entityMetadata.set(DataKeys.AIR_TICKS, 100);
        entityMetadata.set(DataKeys.POSE, Pose.STANDING);
        entityMetadata.set(DataKeys.SILENT, false);
        entityMetadata.set(DataKeys.NO_GRAVITY, false);
        entityMetadata.set(DataKeys.FROZEN_TICKS, 0);
        entityMetadata.set(DataKeys.FLAGS, (byte) 0);
    }

    public boolean spawn(@NotNull Location location) {
        if (spawned) return false;

        this.spawned = true;

        EntityStorage.addEntity(this);
        return true;
    }

    public boolean despawn() {
        if (!spawned) return false;

        EntityStorage.removeEntity(this);
        clearViewers();

        spawned = false;
        return true;
    }

    public void addViewer(@NotNull Player player) {
        if (!spawned) return;

        if (viewers.add(player)) {
            PacketUtil.sendBundledPackets(player, PacketUtil.createSpawnPacket(this), entityMetadata.createPacket());
            onViewerAdded(player);
        }
    }

    public void removeViewer(@NotNull Player player) {
        if (viewers.remove(player)) {
            PacketUtil.sendPacket(player, new ClientboundRemoveEntitiesPacket(this.entityId));
            onViewerRemoved(player);
        }
    }

    public void clearViewers() {
        viewers.forEach(this::removeViewer);
        viewers.clear();
    }

    public abstract void onViewerAdded(Player player);

    public abstract void onViewerRemoved(Player player);

    public void teleport(@NotNull Location location) {
        if (!spawned) return;
        
        setLocation(location);

        ClientboundEntityPositionSyncPacket teleportPacket = new ClientboundEntityPositionSyncPacket(
                entityId,
                new PositionMoveRotation(
                        new Vec3(location.getX(), location.getY(), location.getZ()),
                        Vec3.ZERO,
                        location.getYaw(),
                        location.getPitch()
                ),
                true
        );
        PacketUtil.sendPacket(getViewers(), teleportPacket);
    }

    public void addPassenger(int entityId) {
        if (passengers.contains(entityId)) {
            throw new IllegalArgumentException("This passenger is already riding this entity!");
        }

        passengers.add(entityId);
        PacketUtil.sendPacket(getViewers(), PacketUtil.createPassengerPacket(this));

        AbstractEntity passenger = EntityStorage.getEntity(entityId);
        if (passenger != null && passenger.isSpawned()) {
            passenger.ridingEntityId = entityId;
        }
    }

    public void removePassenger(int entityId) {
        if (!passengers.contains(entityId)) {
            throw new IllegalArgumentException("This passenger doesn't exist.");
        }

        passengers.remove(entityId);
        PacketUtil.sendPacket(getViewers(), PacketUtil.createPassengerPacket(this));

        AbstractEntity passenger = EntityStorage.getEntity(entityId);
        if (passenger != null && passenger.isSpawned()) {
            passenger.ridingEntityId = -1;
        }
    }

    public List<Integer> getPassengers() {
        return new ArrayList<>(passengers);
    }

    public int getEntityId() {
        return entityId;
    }

    public @NotNull UUID getEntityUUID() {
        return entityUUID;
    }

    public @NotNull EntityType getEntityType() {
        return entityType;
    }

    public @NotNull Set<Player> getViewers() {
        return Set.copyOf(viewers);
    }

    public @Nullable Location getLocation() {
        return location;
    }

    public void setLocation(@NotNull Location location) {
        this.location = location;
    }

    public boolean isSpawned() {
        return spawned;
    }
}
