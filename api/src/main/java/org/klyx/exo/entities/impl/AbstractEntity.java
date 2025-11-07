package org.klyx.exo.entities.impl;

import org.bukkit.entity.EntityType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.klyx.exo.data.entity.EntityState;
import org.klyx.exo.data.entity.StateTransition;
import org.klyx.exo.data.keys.DataKey;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.data.metadata.EntityMetadata;
import org.klyx.exo.entities.impl.components.MountComponent;
import org.klyx.exo.entities.impl.components.SpatialComponent;
import org.klyx.exo.entities.impl.components.ViewerRegistry;
import org.klyx.exo.storage.EntityStorage;
import org.klyx.exo.utils.ConversionUtil;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class AbstractEntity {

    protected final int entityId;
    protected final @NotNull String name;
    protected final @NotNull UUID entityUUID;
    protected final @NotNull EntityType entityType;

    protected EntityState state = EntityState.NONE;
    private final Map<EntityState, List<Consumer<StateTransition>>> stateListeners = new EnumMap<>(EntityState.class);

    // Components
    private final ViewerRegistry viewerRegistry;
    private final SpatialComponent spatialComponent;
    private final MountComponent mountComponent;
    protected final @NotNull EntityMetadata entityMetadata;

    public AbstractEntity(@NotNull EntityType entityType) {
        this(EntityStorage.newEntityId(), UUID.randomUUID(), entityType);
    }

    public AbstractEntity(int entityId, @NotNull UUID entityUUID, @NotNull EntityType entityType) {
        this.entityId = entityId;
        this.entityUUID = entityUUID;
        this.entityType = entityType;
        this.name = "entity-" + entityUUID.toString().substring(0, 8);

        this.entityMetadata = new EntityMetadata(entityId);
        this.viewerRegistry = new ViewerRegistry(this);
        this.spatialComponent = new SpatialComponent(this);
        this.mountComponent = new MountComponent(this);

        initDefaultMetadata();
        applyExtraMetadata();
    }

    protected void initDefaultMetadata() {
        setMetadata(DataKeys.Entity.AIR_TICKS);
        setMetadata(DataKeys.Entity.POSE, ConversionUtil.bukkitToMinecraft(Pose.STANDING));
        setMetadata(DataKeys.Entity.SILENT);
        setMetadata(DataKeys.Entity.NO_GRAVITY);
        setMetadata(DataKeys.Entity.FROZEN_TICKS);
        setMetadata(DataKeys.Entity.FLAGS);
    }

    public abstract void applyExtraMetadata();

    public <T> void setMetadata(DataKey<T> key, T value) {
        entityMetadata.set(key, value);
    }

    public <T> void setMetadata(DataKey<T> key) {
        entityMetadata.set(key);
    }

    public void transitionEntityState(@NotNull EntityState newState) {
        if (state == newState) return;

        this.state = newState;
        StateTransition transition = new StateTransition(state, newState, System.currentTimeMillis());

        List<Consumer<StateTransition>> listeners = stateListeners.get(newState);
        if (listeners != null) {
            listeners.forEach(listener -> listener.accept(transition));
        }
    }

    public void onStateEnter(@NotNull EntityState state, @NotNull Consumer<StateTransition> listener) {
        stateListeners.computeIfAbsent(state, k -> new ArrayList<>()).add(listener);
    }

    public boolean isAlive() {
        return state == EntityState.ALIVE;
    }

    public boolean spawn(@NotNull Location location) {
        if (state != EntityState.NONE) return false;

        transitionEntityState(EntityState.SPAWNING);

        spatialComponent.updateLocation(location);
        EntityStorage.addEntity(this);

        transitionEntityState(EntityState.ALIVE);
        return true;
    }

    public boolean despawn() {
        if (state != EntityState.ALIVE) return false;

        transitionEntityState(EntityState.DESPAWNING);

        viewerRegistry.clearAll();
        mountComponent.clearAll();
        EntityStorage.removeEntity(this);

        transitionEntityState(EntityState.DESTROYED);
        return true;
    }

    public abstract void onSpawn();

    public abstract void onDespawn();

    public void showTo(@NotNull Player player) {
        viewerRegistry.addViewer(player);
    }

    public void hideFrom(@NotNull Player player) {
        viewerRegistry.removeViewer(player);
    }

    public @NotNull Set<Player> getViewers() {
        return viewerRegistry.getViewers();
    }

    public boolean hasViewer(@NotNull Player player) {
        return viewerRegistry.hasViewer(player);
    }

    public abstract void onViewerAdded(Player player);

    public abstract void onViewerRemoved(Player player);

    public void teleport(@NotNull Location location) {
        spatialComponent.teleportTo(location);
    }

    public @Nullable Location getLocation() {
        return spatialComponent.getLocation();
    }

    public void updateLocation(@NotNull Location location) {
        spatialComponent.updateLocation(location);
    }

    public void addPassenger(int passengerId) {
        mountComponent.addPassenger(passengerId);
    }

    public void addPassenger(AbstractEntity passengerEntity) {
        mountComponent.addPassenger(passengerEntity.getEntityId());
    }

    public void removePassenger(int passengerId) {
        mountComponent.removePassenger(passengerId);
    }

    public void removePassenger(AbstractEntity passengerEntity) {
        mountComponent.removePassenger(passengerEntity.getEntityId());
    }

    public List<Integer> getPassengers() {
        return mountComponent.getPassengers();
    }

    public void setVehicle(int vehicleId) {
        mountComponent.setRidingEntityId(vehicleId);
    }

    public @Nullable AbstractEntity getVehicle() {
        return EntityStorage.getEntity(mountComponent.getRidingEntityId());
    }

    public AbstractEntity getEntity() {return this;}

    public int getEntityId() {
        return entityId;
    }

    public @NotNull UUID getEntityUUID() {
        return entityUUID;
    }

    public @NotNull EntityType getEntityType() {
        return entityType;
    }

    public EntityState getState() {
        return state;
    }

    @ApiStatus.Internal
    public SpatialComponent getSpatialComponent() {
        return spatialComponent;
    }

    @ApiStatus.Internal
    public MountComponent getMountComponent() {
        return mountComponent;
    }

    @ApiStatus.Internal
    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }
}
