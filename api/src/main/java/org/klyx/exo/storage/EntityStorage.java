package org.klyx.exo.storage;

import org.jetbrains.annotations.NotNull;
import org.klyx.exo.entities.AbstractEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class EntityStorage {

    private static final Map<Integer, AbstractEntity> entities = new ConcurrentHashMap<>();

    private static final AtomicInteger entityIds = new AtomicInteger(1000000);

    public static int newEntityId() {
        return entityIds.incrementAndGet();
    }

    public static void addEntity(@NotNull AbstractEntity entity) {
        entities.put(entity.getEntityId(), entity);
    }

    public static void removeEntity(@NotNull AbstractEntity entity) {
        entities.remove(entity.getEntityId());
    }

    public static void removeEntity(int entityId) {
        entities.remove(entityId);
    }

    public static AbstractEntity getEntity(int entityId) {
        return entities.get(entityId);
    }

}
