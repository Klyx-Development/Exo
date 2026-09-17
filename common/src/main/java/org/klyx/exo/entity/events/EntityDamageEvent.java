package org.klyx.exo.entity.events;

import org.jspecify.annotations.Nullable;
import org.klyx.exo.event.CancellableEvent;
import org.klyx.exo.player.ExoPlayer;

public class EntityDamageEvent extends CancellableEvent {

    private final @Nullable ExoPlayer attacker;
    private final float amount;

    public EntityDamageEvent(@Nullable ExoPlayer attacker, float amount) {
        this.attacker = attacker;
        this.amount = amount;
    }

    public @Nullable ExoPlayer attacker() {
        return attacker;
    }

    public float amount() {
        return amount;
    }
}
