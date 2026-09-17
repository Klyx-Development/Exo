package org.klyx.exo.entity.events;

import org.jspecify.annotations.Nullable;
import org.klyx.exo.event.CancellableEvent;
import org.klyx.exo.player.ExoPlayer;

public class EntityDeathEvent extends CancellableEvent {

    private final @Nullable ExoPlayer killer;

    public EntityDeathEvent(@Nullable ExoPlayer killer) {
        this.killer = killer;
    }

    public @Nullable ExoPlayer killer() {
        return killer;
    }
}
