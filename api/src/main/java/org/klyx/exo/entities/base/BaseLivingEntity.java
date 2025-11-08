package org.klyx.exo.entities.base;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.klyx.exo.entities.impl.AbstractLivingEntity;

import java.util.Optional;
import java.util.function.Consumer;

public class BaseLivingEntity extends AbstractLivingEntity {
    private Consumer<Optional<?>> onSpawn;
    private Consumer<Optional<?>> onDespawn;
    private Consumer<Player> onViewerAdded;
    private Consumer<Player> onViewerRemoved;

    public BaseLivingEntity(@NotNull EntityType entityType) {
        super(entityType);
    }

    @Override
    protected final void initDefaultMetadata() {
        super.initDefaultMetadata();
    }

    @Override
    public void applyExtraMetadata() {}

    @Override
    public void onSpawn() {
        if (onSpawn != null) {
            onSpawn.accept(Optional.empty());
        }
    }

    public void onSpawn(Consumer<Optional<?>> consumer) {
        this.onSpawn = consumer;
    }

    @Override
    public void onDespawn() {
        if (onDespawn != null) {
            onDespawn.accept(Optional.empty());
        }
    }

    public void onDespawn(Consumer<Optional<?>> consumer) {
        this.onDespawn = consumer;
    }

    @Override
    public void onViewerAdded(Player player) {
        if (onViewerAdded != null) {
            onViewerAdded.accept(player);
        }
    }

    public void onViewerAdded(Consumer<Player> callback) {
        this.onViewerAdded = callback;
    }

    @Override
    public void onViewerRemoved(Player player) {
        if (onViewerRemoved != null) {
            onViewerRemoved.accept(player);
        }
    }

    public void onViewerRemoved(Consumer<Player> callback) {
        this.onViewerRemoved = callback;
    }
}
