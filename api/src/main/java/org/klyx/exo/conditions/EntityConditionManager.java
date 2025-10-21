package org.klyx.exo.conditions;

import org.bukkit.entity.Player;
import org.klyx.exo.entities.AbstractEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Similar to EntityLib's ViewerEngine, This class is used to add conditions for when a player can/can't see an entity.
 */
public class EntityConditionManager {
    private static AbstractEntity entity;
    private static final Map<Player, EntityCondition> conditions = new ConcurrentHashMap<>();

    public EntityConditionManager(AbstractEntity entity) {
        EntityConditionManager.entity = entity;
    }

    public void addCondition(Player player, EntityCondition condition) {
        conditions.put(player, condition);
        if (condition.shouldSee(player, entity)) {
            entity.addViewer(player);
        }
    }

    public void removeCondition(Player player, EntityCondition condition) {
        conditions.remove(player, condition);
        entity.removeViewer(player);
    }

    public void removeAllConditions(Player player) {
        conditions.remove(player);
        entity.removeViewer(player);
    }

    // TODO make a class that automatically starts bukkit tasks using the instance of the plugin that we're hooking into.
    public static void updateAllConditions() {
        if (conditions.isEmpty() || !entity.isSpawned()) return;

        conditions.forEach((player, condition) -> {
            boolean shouldSee = condition.shouldSee(player, entity);
            boolean isAlreadyViewing = entity.getViewers().contains(player);

            if (shouldSee && !isAlreadyViewing) {
                entity.addViewer(player);
            } else if (!shouldSee && isAlreadyViewing){
                entity.removeViewer(player);
            }
        });
    }

}
