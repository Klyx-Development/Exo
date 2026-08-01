package org.klyx.exo.entity;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.world.phys.Vec3;
import org.bukkit.World;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class EntityManager {

    private final Map<World, Set<ExoEntity>> entitiesByWorld = new ConcurrentHashMap<>();
    private final Map<UUID, ExoEntity> entitiesByUUID = new ConcurrentHashMap<>();
    private final Int2ObjectMap<ExoEntity> entitiesByIdMap = new Int2ObjectOpenHashMap<>();
    private final EntityChunkTracker chunkTracker = new EntityChunkTracker();

    void addEntity(ExoEntity entity) {
        entitiesByUUID.putIfAbsent(entity.uuid(), entity);
        synchronized (entitiesByIdMap) {
            entitiesByIdMap.put(entity.entityId(), entity);
        }
    }

    void removeEntity(ExoEntity entity) {
        entitiesByUUID.remove(entity.uuid());
        synchronized (entitiesByIdMap) {
            entitiesByIdMap.remove(entity.entityId());
        }
        if (entity.isSpawned()) {
            untrackWorldPosition(entity);
        }
    }

    void trackWorldPosition(ExoEntity entity) {
        Vec3 pos = entity.getWorldStateManager().getWorldState().currentPos();
        chunkTracker.add(entity, pos);
        entitiesByWorld.computeIfAbsent(entity.getWorldStateManager().getWorldState().currentWorld(), w -> ConcurrentHashMap.newKeySet()).add(entity);
    }

    void untrackWorldPosition(ExoEntity entity) {
        Vec3 pos = entity.getWorldStateManager().getWorldState().currentPos();
        World world = entity.getWorldStateManager().getWorldState().currentWorld();

        this.chunkTracker.remove(entity, pos);

        Set<ExoEntity> worldSet = this.entitiesByWorld.get(world);
        if (worldSet != null) {
            worldSet.remove(entity);
            if (worldSet.isEmpty()) this.entitiesByWorld.remove(world);
        }
    }

    @ApiStatus.Internal
    public void destroy() {
        synchronized (this.entitiesByIdMap) {
            this.entitiesByIdMap.clear();
        }
        this.entitiesByUUID.clear();
        this.chunkTracker.destroy();
        this.entitiesByWorld.clear();
    }

    public @Nullable ExoEntity getEntity(UUID uuid) {
        return this.entitiesByUUID.get(uuid);
    }

    public @Nullable ExoEntity getEntity(int entityId) {
        synchronized (this.entitiesByIdMap) {
            return this.entitiesByIdMap.get(entityId);
        }
    }

    public boolean containsEntity(UUID uuid) {
        return this.entitiesByUUID.containsKey(uuid);
    }

    public boolean containsEntity(int entityId) {
        synchronized (this.entitiesByIdMap) {
            return this.entitiesByIdMap.containsKey(entityId);
        }
    }

    public @UnmodifiableView Collection<ExoEntity> getEntities() {
        synchronized (this.entitiesByIdMap) {
            return List.copyOf(this.entitiesByIdMap.values());
        }
    }

    public @UnmodifiableView Collection<ExoEntity> getEntitiesInChunk(int chunkX, int chunkZ) {
        return this.chunkTracker.getEntitiesInChunk(chunkX, chunkZ);
    }

    public @UnmodifiableView Collection<ExoEntity> getEntitiesInWorld(World world) {
        Set<ExoEntity> set = this.entitiesByWorld.get(world);
        return set != null ? Collections.unmodifiableCollection(set) : Collections.emptySet();
    }

    @ApiStatus.Internal
    public void updateEntityChunk(ExoEntity entity, Vec3 oldPosition, Vec3 newPosition) {
        this.chunkTracker.update(entity, oldPosition, newPosition);
    }

    @ApiStatus.Internal
    public void updateEntityWorld(ExoEntity entity, World oldWorld, Vec3 oldPos,
                                  World newWorld, Vec3 newPos) {
        Set<ExoEntity> oldSet = this.entitiesByWorld.get(oldWorld);
        if (oldSet != null) {
            oldSet.remove(entity);
            if (oldSet.isEmpty()) this.entitiesByWorld.remove(oldWorld);
        }
        this.entitiesByWorld.computeIfAbsent(newWorld, w -> ConcurrentHashMap.newKeySet())
                .add(entity);
        this.chunkTracker.remove(entity, oldPos);
        this.chunkTracker.add(entity, newPos);
    }

}