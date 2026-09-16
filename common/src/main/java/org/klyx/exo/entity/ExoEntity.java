package org.klyx.exo.entity;

import org.jetbrains.annotations.UnmodifiableView;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.data.EntityId;
import org.klyx.exo.entity.components.EntityComponent;
import org.klyx.exo.entity.components.EntityComponentManager;
import org.klyx.exo.entity.data.EntityData;
import org.klyx.exo.entity.data.ExoEntityType;
import org.klyx.exo.entity.data.attribute.AttributeStateManager;
import org.klyx.exo.entity.data.attribute.ExoAttribute;
import org.klyx.exo.entity.data.attribute.ExoAttributeModifier;
import org.klyx.exo.entity.data.attribute.ExoAttributeSnapshot;
import org.klyx.exo.entity.data.object.AbstractObjectData;
import org.klyx.exo.entity.data.world.EntityWorldState;
import org.klyx.exo.entity.data.world.EntityWorldStateManager;
import org.klyx.exo.entity.events.EntityDespawnEvent;
import org.klyx.exo.entity.events.EntitySpawnEvent;
import org.klyx.exo.entity.meta.impl.AbstractEntityMeta;
import org.klyx.exo.entity.meta.impl.MetaEntry;
import org.klyx.exo.entity.viewer.ViewerManager;
import org.klyx.exo.entity.viewer.ViewerRule;
import org.klyx.exo.event.Event;
import org.klyx.exo.event.EventBus;
import org.klyx.exo.player.ExoPlayer;
import org.klyx.exo.world.ExoWorld;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class ExoEntity {

    private final EntityComponentManager entityComponentManager;
    private final ViewerManager viewerManager;
    private final AttributeStateManager attributesStateManager;
    private @Nullable EntityWorldStateManager worldStateManager;

    private final int entityId;
    private final UUID uuid;
    private final boolean tracked;

    private volatile @Nullable EntityData entityData;
    private boolean resolvingEntityData;

    private volatile boolean isSpawned;

    protected ExoEntity() {
        this(true);
    }

    protected ExoEntity(boolean tracked) {
        this.entityComponentManager = new EntityComponentManager();
        this.viewerManager = new ViewerManager(this, new ArrayList<>(), new ArrayList<>());
        this.attributesStateManager = new AttributeStateManager(this, List.of());

        this.entityId = EntityId.next();
        this.uuid = UUID.randomUUID();
        this.tracked = tracked;

        if (tracked) {
            Exo.entityManager().addEntity(this);
        }
    }

    public abstract EntityData.Builder define();

    public final EntityData entityData() {
        EntityData resolved = this.entityData;
        return resolved != null ? resolved : resolveEntityData();
    }

    private synchronized EntityData resolveEntityData() {
        EntityData resolved = this.entityData;
        if (resolved != null) return resolved;

        if (resolvingEntityData) {
            throw new IllegalStateException("define() of " + getClass().getName() + " must not re-read the entity data from within the method");
        }

        resolvingEntityData = true;
        try {
            EntityData.Builder builder = define();
            resolved = builder.build();
            this.entityData = resolved;
        } finally {
            resolvingEntityData = false;
        }

        resolved.getComponents().forEach(entityComponentManager::addComponent);
        entityComponentManager.attachAll(this);

        return resolved;
    }

    public int entityId() {
        return entityId;
    }

    public UUID uuid() {
        return uuid;
    }

    public ExoEntityType entityType() {
        return entityData().getType();
    }

    public EventBus eventBus() {
        entityData();
        return entityComponentManager.eventBus();
    }

    public boolean isSpawned() {
        return isSpawned;
    }

    public boolean isTracked() {
        return tracked;
    }

    public <C extends EntityComponent> @Nullable C getComponent(Class<C> componentClass) {
        entityData();
        return entityComponentManager.getComponent(componentClass);
    }

    public <C extends EntityComponent> ExoEntity editComponent(Class<C> componentClass, Consumer<C> editor) {
        entityData();
        entityComponentManager.editComponent(componentClass, editor);
        return this;
    }

    public boolean hasComponent(Class<? extends EntityComponent> componentClass) {
        entityData();
        return entityComponentManager.hasComponent(componentClass);
    }

    public @UnmodifiableView Collection<EntityComponent> getComponents() {
        entityData();
        return entityComponentManager.getComponents();
    }

    public <M extends AbstractEntityMeta> @Nullable M getMeta(Class<M> expected) {
        return entityData().getMeta(expected);
    }

    public @Nullable AbstractEntityMeta entityMeta() {
        return entityData().getMeta();
    }

    public <M extends AbstractEntityMeta> ExoEntity editMeta(Class<M> metaClass, Consumer<M> editor) {
        M meta = getMeta(metaClass);
        if (meta == null) {
            throw new IllegalStateException("No meta of type " + metaClass.getSimpleName() + " defined for this entity");
        }
        editor.accept(meta);

        if (isSpawned) {
            List<MetaEntry<?>> dirty = meta.dirtyEntries();
            if (!dirty.isEmpty()) {
                Exo.platform().metadataDispatcher().dispatchDirty(this, dirty);
            }
        }

        meta.markSynced();
        return this;
    }

    public @Nullable AbstractObjectData getObjectData() {
        return entityData().getObjectData();
    }

    public int objectDataValue() {
        return entityData().getObjectDataValue();
    }

    public @Nullable Consumer<ExoEntity> getOnShow() {
        return entityData().getOnShow();
    }

    public @Nullable Consumer<ExoEntity> getOnHide() {
        return entityData().getOnHide();
    }

    public ExoEntity setAttribute(ExoAttribute attribute, double value) {
        attributesStateManager.setAttribute(attribute, value);
        return this;
    }

    public ExoEntity setAttribute(ExoAttribute attribute, double value, ExoAttributeModifier modifier) {
        attributesStateManager.setAttribute(attribute, value, modifier);
        return this;
    }

    public ExoEntity removeAttribute(ExoAttribute attribute) {
        attributesStateManager.removeAttribute(attribute);
        return this;
    }

    public ViewerManager getViewerManager() {
        return viewerManager;
    }

    public Set<UUID> getViewers() {
        return viewerManager.getViewers();
    }

    public Set<UUID> getActiveViewers() {
        return viewerManager.getActiveViewers();
    }

    public Set<UUID> getUnloadedViewers() {
        return viewerManager.getUnloadedViewers();
    }

    public Set<UUID> getExplicitViewers() {
        return viewerManager.getExplicitViewers();
    }

    public boolean isViewer(UUID uuid) {
        return viewerManager.isViewer(uuid);
    }

    public int getViewerCount() {
        return viewerManager.getViewerCount();
    }

    public boolean isRestrictedToExplicitViewers() {
        return viewerManager.isRestrictedToExplicitViewers();
    }

    public ExoEntity addViewer(UUID playerUUID) {
        viewerManager.addExplicitViewer(playerUUID);
        return this;
    }

    public ExoEntity addViewer(ExoPlayer player) {
        viewerManager.addExplicitViewer(player.uuid());
        return this;
    }

    public ExoEntity addViewers(Collection<UUID> playerUUIDs) {
        playerUUIDs.forEach(viewerManager::addExplicitViewer);
        return this;
    }

    public ExoEntity removeViewer(UUID playerUUID) {
        viewerManager.removeExplicitViewer(playerUUID);
        return this;
    }

    public ExoEntity clearViewerRestriction() {
        viewerManager.clearExplicitViewers();
        return this;
    }

    public ExoEntity updateViewer(ExoPlayer player) {
        viewerManager.updateViewer(player);
        return this;
    }

    public ViewerRule addRule(Predicate<ExoPlayer> rule) {
        return viewerManager.addRule(rule);
    }

    public <T extends Event> ViewerRule addViewersUpdateTrigger(EventBus bus, Class<T> eventClass, Function<T, Collection<ExoPlayer>> playerExtractor) {
        return viewerManager.addViewersUpdateTrigger(bus, eventClass, playerExtractor);
    }

    public EntityWorldStateManager getWorldStateManager() {
        if (!isSpawned || worldStateManager == null) throw new IllegalStateException("Entity is not spawned");
        return worldStateManager;
    }

    public ExoWorld getWorld() {
        return worldStateManager.getWorldState().currentWorld();
    }

    public ExoPos getLocation() {
        EntityWorldState position = worldStateManager.getWorldState();
        return position.asExoPos();
    }

    public ExoEntity teleport(ExoWorld world, ExoPos pos) {
        worldStateManager.teleport(world, pos);
        return this;
    }

    public float getYaw() {
        EntityWorldState position = this.worldStateManager.getWorldState();
        return position.currentYaw();
    }

    public ExoEntity setYaw(float yaw) {
        this.worldStateManager.setYaw(yaw);
        return this;
    }

    public float getPitch() {
        EntityWorldState position = this.worldStateManager.getWorldState();
        return position.currentPitch();
    }

    public ExoEntity setPitch(float pitch) {
        this.worldStateManager.setPitch(pitch);
        return this;
    }

    public ExoEntity lookAt(ExoVec3d position) {
        worldStateManager.lookAt(position);
        return this;
    }

    public float getVerticalHeadRot() {
        EntityWorldState position = this.worldStateManager.getWorldState();
        return position.currentVerticalHeadRot();
    }

    public ExoEntity setVerticalHeadRot(float verticalHeadRot) {
        this.worldStateManager.setVerticalHeadRot(verticalHeadRot);
        return this;
    }

    public boolean isOnGround() {
        EntityWorldState position = this.worldStateManager.getWorldState();
        return position.currentOnGround();
    }

    public ExoEntity setOnGround(boolean onGround) {
        this.worldStateManager.setOnGround(onGround);
        return this;
    }

    public @Nullable ExoVec3d getVelocity() {
        EntityWorldState position = this.worldStateManager.getWorldState();
        return position.currentVelocity();
    }

    public ExoEntity setVelocity(ExoVec3d velocity) {
        this.worldStateManager.setVelocity(velocity);
        return this;
    }

    public ExoEntity setWorld(ExoWorld world) {
        worldStateManager.setWorld(world);
        return this;
    }

    public ExoEntity spawn(ExoWorld world, ExoPos pos) {
        if (isSpawned) return this;

        entityData();

        EntitySpawnEvent spawnEvent = new EntitySpawnEvent();
        eventBus().post(spawnEvent);

        if (spawnEvent.isCancelled()) return this;

        if (worldStateManager == null) {
            worldStateManager = new EntityWorldStateManager(this, world, pos);
        } else {
            worldStateManager.teleport(world, pos);
        }

        isSpawned = true;
        if (tracked) {
            Exo.entityManager().trackWorldPosition(this);
        }

        viewerManager.registerAll();
        worldStateManager.markSynced();

        AbstractEntityMeta meta = entityMeta();
        if (meta != null) {
            meta.markSynced();
        }

        List<ExoAttributeSnapshot> currentProps =
                new ArrayList<>(attributesStateManager.getAttributesState().currentProperties().values());
        if (!currentProps.isEmpty()) {
            Exo.platform().attributeDispatcher().dispatchDirty(this, currentProps);
        }
        attributesStateManager.markSynced();

        return this;
    }

    public ExoEntity despawn() {
        if (!isSpawned) return this;

        EntityDespawnEvent event = new EntityDespawnEvent();
        entityComponentManager.eventBus().post(event);
        if (event.isCancelled()) return this;

        if (tracked) {
            Exo.entityManager().untrackWorldPosition(this);
        }

        isSpawned = false;
        viewerManager.unregisterAll();
        return this;
    }

    public void destroy() {
        despawn();
        entityComponentManager.detachAll(this);
        viewerManager.destroy();
        entityComponentManager.destroy();
        if (tracked) {
            Exo.entityManager().removeEntity(this);
        }
    }
}
