package org.klyx.exo.entity.events;

import org.bukkit.entity.Player;
import org.klyx.exo.event.Event;

public record EntityAttackEvent(Player attacker) implements Event {
}
