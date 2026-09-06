package org.klyx.exo.entity.events;

import org.klyx.exo.event.Event;
import org.klyx.exo.player.ExoPlayer;

public record EntityAttackEvent(ExoPlayer attacker) implements Event {
}
