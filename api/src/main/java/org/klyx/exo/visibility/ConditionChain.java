package org.klyx.exo.visibility;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.klyx.exo.entities.impl.AbstractEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConditionChain {
    // Used for when a condition chain should be applied to all players
    static final UUID ALL_PLAYERS_KEY = new UUID(0, 0);

    private final List<VisibilityRule> rules = new ArrayList<>();

    public void addRule(@NotNull VisibilityRule rule) {
        rules.add(rule);
    }

    public void removeRule(@NotNull VisibilityRule rule) {
        rules.remove(rule);
    }

    public boolean isEmpty() {
        return rules.isEmpty();
    }

    public boolean test(@NotNull Player player, @NotNull AbstractEntity entity) {
        for (VisibilityRule rule : rules) {
            if (!rule.test(player, entity)) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        return rules.size();
    }

}
