package org.klyx.exo.entity;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.UnmodifiableView;
import org.klyx.exo.util.ChunkKeyUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EntityChunkTracker {

    private final Long2ObjectMap<Set<ExoEntity>> entitiesByChunk = new Long2ObjectOpenHashMap<>();

    void add(ExoEntity entity, Vec3 position) {
        long key = ChunkKeyUtils.toLongKey(position);
        synchronized (entitiesByChunk) {
            entitiesByChunk.computeIfAbsent(key, k -> ConcurrentHashMap.newKeySet()).add(entity);
        }
    }

    void remove(ExoEntity entity, Vec3 position) {
        long key = ChunkKeyUtils.toLongKey(position);
        synchronized (entitiesByChunk) {
            Set<ExoEntity> entities = entitiesByChunk.get(key);
            //noinspection ConstantConditions
            if (entities == null) return;

            entities.remove(entity);
            if (entities.isEmpty()) {
                entitiesByChunk.remove(key);
            }
        }
    }

    void update(ExoEntity entity, Vec3 newPosition, Vec3 oldPosition) {
        long oldKey = ChunkKeyUtils.toLongKey(oldPosition);
        long newKey = ChunkKeyUtils.toLongKey(newPosition);
        if (oldKey == newKey) return;

        synchronized (entitiesByChunk) {
            Set<ExoEntity> oldEntity = entitiesByChunk.get(oldKey);
            //noinspection ConstantConditions
            if (oldEntity != null) {
                oldEntity.remove(entity);
                if (oldEntity.isEmpty()) {
                    entitiesByChunk.remove(oldKey);
                }
            }

            entitiesByChunk.computeIfAbsent(newKey, k -> ConcurrentHashMap.newKeySet()).add(entity);
        }
    }

    @UnmodifiableView
    Collection<ExoEntity> getEntitiesInChunk(int chunkX, int chunkZ) {
        long key = ChunkKeyUtils.toLongKey(chunkX, chunkZ);
        synchronized (entitiesByChunk) {
            Set<ExoEntity> entities = entitiesByChunk.get(key);
            //noinspection ConstantConditions
            return entities != null ? entities : List.of();
        }
    }

    void destroy() {
        synchronized (entitiesByChunk) {
            entitiesByChunk.clear();
        }
    }

}
