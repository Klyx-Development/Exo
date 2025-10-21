package org.klyx.exo.conditions;

import org.bukkit.entity.Player;
import org.klyx.exo.entities.AbstractEntity;

public interface EntityCondition {
    boolean shouldSee(Player player, AbstractEntity entity);
}
