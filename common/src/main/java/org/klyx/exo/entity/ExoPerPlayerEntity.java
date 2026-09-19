package org.klyx.exo.entity;

import org.jspecify.annotations.Nullable;
import org.klyx.exo.player.ExoPlayer;
import org.klyx.exo.world.ExoWorld;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * An ExoEntity that can be different for different players
 */
public class ExoPerPlayerEntity {

    private final Map<UUID, ExoEntity> entities = new ConcurrentHashMap<>();
    private final Supplier<ExoEntity> entityFactory;

    private @Nullable ExoWorld world;
    private @Nullable ExoPos pos;

    public ExoPerPlayerEntity(Supplier<ExoEntity> entityFactory) {
        this.entityFactory = entityFactory;
    }

    public void spawn(ExoWorld world, ExoPos exoPos) {
        this.world = world;
        this.pos = exoPos;
        executeGlobal(entity -> entity.spawn(world, exoPos));
    }

    public void addViewer(ExoPlayer player) {
        getEntityFor(player.uuid());
    }

    public void removeViewer(ExoPlayer player) {
        ExoEntity entity = entities.remove(player.uuid());
        if (entity == null) return;

        entity.destroy();
    }

    public void executeGlobal(Consumer<ExoEntity> action) {
        entities.values().forEach(action);
    }

    public void forEachEntity(BiConsumer<UUID, ExoEntity> action) {
        entities.forEach(action);
    }

    public void modify(ExoPlayer player, Consumer<ExoEntity> action) {
        action.accept(getEntityFor(player.uuid()));
    }

    public ExoEntity getEntityFor(UUID uuid) {
        return entities.computeIfAbsent(uuid, id -> {
            ExoEntity entity = entityFactory.get();
            entity.addViewer(id);
            if (world != null && pos != null) {
                entity.spawn(world, pos);
            }
            return entity;
        });
    }

    public @Nullable ExoEntity getExistingEntityFor(UUID uuid) {
        return entities.get(uuid);
    }

    public Collection<ExoEntity> getEntities() {
        return List.copyOf(entities.values());
    }

    public void destroy() {
        entities.values().forEach(ExoEntity::destroy);
        entities.clear();
    }
}
