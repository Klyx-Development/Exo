package org.klyx.exo.entity.viewer;

import org.jetbrains.annotations.UnmodifiableView;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.ExoVec3d;
import org.klyx.exo.event.Event;
import org.klyx.exo.event.EventBus;
import org.klyx.exo.event.EventSubscription;
import org.klyx.exo.player.ExoPlayer;
import org.klyx.exo.world.ExoWorld;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

public class ViewerManager {

    protected final ExoEntity entity;
    protected final Set<UUID> viewers = ConcurrentHashMap.newKeySet();
    // viewers that do not have the entity loaded
    protected final Set<UUID> unloadedViewers = ConcurrentHashMap.newKeySet();

    private final Set<UUID> explicitViewers = ConcurrentHashMap.newKeySet();
    private volatile boolean restrictedToExplicitViewers;
    private final List<Predicate<ExoPlayer>> dynamicRules = new CopyOnWriteArrayList<>();
    private final List<EventSubscription> triggerSubscriptions = new CopyOnWriteArrayList<>();

    public ViewerManager(ExoEntity entity, List<Predicate<ExoPlayer>> rules, List<UUID> initialViewers) {
        this.entity = entity;
        this.dynamicRules.addAll(rules);
        this.explicitViewers.addAll(initialViewers);
        this.restrictedToExplicitViewers = !initialViewers.isEmpty();
    }

    public @UnmodifiableView Set<UUID> getViewers() {
        Set<UUID> snapshot = new HashSet<>(this.viewers.size() + this.unloadedViewers.size());
        snapshot.addAll(this.viewers);
        snapshot.addAll(this.unloadedViewers);
        return Collections.unmodifiableSet(snapshot);
    }

    public @UnmodifiableView Set<UUID> getActiveViewers() {
        return Collections.unmodifiableSet(this.viewers);
    }

    public @UnmodifiableView Set<UUID> getUnloadedViewers() {
        return Collections.unmodifiableSet(this.unloadedViewers);
    }

    public @UnmodifiableView Set<UUID> getExplicitViewers() {
        return Collections.unmodifiableSet(this.explicitViewers);
    }

    public boolean isRestrictedToExplicitViewers() {
        return this.restrictedToExplicitViewers;
    }

    public boolean isViewer(UUID uuid) {
        return this.viewers.contains(uuid) || this.unloadedViewers.contains(uuid);
    }

    public int getViewerCount() {
        return this.viewers.size() + this.unloadedViewers.size();
    }

    public void addExplicitViewer(UUID playerUUID) {
        boolean becameRestricted = !this.restrictedToExplicitViewers;
        this.restrictedToExplicitViewers = true;
        boolean added = this.explicitViewers.add(playerUUID);

        if (becameRestricted) {
            syncViewers();
            return;
        }

        if (!added) return;
        ExoPlayer player = Exo.platform().viewerVisibilityQuery().resolvePlayer(playerUUID);
        if (player != null) updateViewer(player);
    }

    public void removeExplicitViewer(UUID playerUUID) {
        this.explicitViewers.remove(playerUUID);
        removeViewer(playerUUID, false);
    }

    public void clearExplicitViewers() {
        if (!this.restrictedToExplicitViewers) return;

        this.explicitViewers.clear();
        this.restrictedToExplicitViewers = false;
        syncViewers();
    }

    public void addViewer(UUID playerUUID, boolean isChunkLoad) {
        if (viewers.contains(playerUUID)) return;

        if (!entity.isSpawned()) {
            this.unloadedViewers.add(playerUUID);
            return;
        }

        if (entity.getOnShow() != null) entity.getOnShow().accept(entity);

        boolean shown = Exo.platform().viewerDispatch().onViewerAdded(entity, playerUUID, isChunkLoad);

        this.unloadedViewers.remove(playerUUID);
        if (!shown) return;

        this.viewers.add(playerUUID);
    }

    public void removeViewer(UUID playerUuid, boolean isChunkUnload) {
        boolean wasActive = this.viewers.contains(playerUuid);
        boolean wasUnloaded = this.unloadedViewers.contains(playerUuid);

        if (!wasActive && !wasUnloaded) return;

        if (!wasActive) {
            this.unloadedViewers.remove(playerUuid);
            return;
        }

        if (entity.getOnHide() != null) entity.getOnHide().accept(entity);

        boolean hidden = Exo.platform().viewerDispatch().onViewerRemoved(entity, playerUuid, isChunkUnload);
        if (!hidden) return;

        this.viewers.remove(playerUuid);

        if (isChunkUnload) {
            this.unloadedViewers.add(playerUuid);
        }
    }

    /**
     * Forces the entity to be shown to the player, bypassing chunk/tracking systems
     */
    public void forceShow(ExoPlayer player) {
        addViewer(player.uuid(), false);
    }

    public void forceHide(ExoPlayer player) {
        removeViewer(player.uuid(), false);
    }

    public void updateViewer(ExoPlayer player) {
        if (!entity.isSpawned()) return;

        UUID playerUUID = player.uuid();
        boolean isTracking = isViewer(playerUUID);

        if (shouldSee(player)) {
            boolean wasOffline = unloadedViewers.contains(playerUUID);
            addViewer(playerUUID, wasOffline);
        } else {
            if (isTracking) removeViewer(playerUUID, false);
        }
    }

    public ViewerRule addRule(Predicate<ExoPlayer> rule) {
        dynamicRules.add(rule);
        syncViewers();

        return () -> {
            if (dynamicRules.remove(rule)) syncViewers();
        };
    }

    public <T extends Event> ViewerRule addViewersUpdateTrigger(EventBus bus, Class<T> eventClass, Function<T, Collection<ExoPlayer>> playerExtractor) {
        EventSubscription sub = bus.subscribe(eventClass, event -> {
            Collection<ExoPlayer> playersToUpdate = playerExtractor.apply(event);
            if (!playersToUpdate.isEmpty()) {
                playersToUpdate.forEach(this::updateViewer);
            }
        });

        triggerSubscriptions.add(sub);

        return () -> {
            sub.unsubscribe();
            this.triggerSubscriptions.remove(sub);
        };
    }

    private boolean shouldSee(ExoPlayer player) {
        ExoWorld userWorld = player.world();
        ExoWorld entityWorld = entity.getWorldStateManager().getWorldState().currentWorld();
        if (!userWorld.equals(entityWorld)) {
            return false;
        }

        if (restrictedToExplicitViewers && !explicitViewers.contains(player.uuid())) {
            return false;
        }

        for (Predicate<ExoPlayer> rule : dynamicRules) {
            try {
                if (!rule.test(player)) return false;
            } catch (Exception e) {
                Exo.logger().error("An error occurred while evaluating a viewer rule for {}: ", player.name(), e);
                return false;
            }
        }

        return true;
    }

    public void registerAll() {
        syncViewers();
    }

    private void syncViewers() {
        if (!entity.isSpawned()) return;

        ExoWorld world = entity.getWorldStateManager().getWorldState().currentWorld();
        ExoVec3d pos = entity.getWorldStateManager().getWorldState().currentPos();
        int chunkX = (int) Math.floor(pos.x()) >> 4;
        int chunkZ = (int) Math.floor(pos.z()) >> 4;

        for (ExoPlayer player : Exo.platform().viewerVisibilityQuery().playersSeeingChunk(world, chunkX, chunkZ)) {
            updateViewer(player);
        }
    }

    public void unregisterAll() {
        Set<UUID> viewers = new HashSet<>(this.viewers);
        viewers.addAll(this.unloadedViewers);

        for (UUID viewer : viewers) {
            this.removeViewer(viewer, false);
        }
    }

    public void handleLoad(ExoPlayer player) {
        updateViewer(player);
    }

    public void handleUnload(ExoPlayer player) {
        removeViewer(player.uuid(), true);
    }

    public void destroy() {
        this.viewers.clear();
        this.unloadedViewers.clear();

        for (EventSubscription sub : this.triggerSubscriptions) {
            sub.unsubscribe();
        }

        this.triggerSubscriptions.clear();
        this.dynamicRules.clear();
        this.explicitViewers.clear();
        this.restrictedToExplicitViewers = false;
    }

}
