package org.klyx.exo.visibility;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.klyx.exo.entities.base.AbstractEntity;

import java.util.function.BiPredicate;

@FunctionalInterface
public interface VisibilityRule extends BiPredicate<Player, AbstractEntity> {
    @Override
    boolean test(Player player, AbstractEntity entity);

    static VisibilityRule always() {
        return (player, entity) -> true;
    }

    static VisibilityRule distance(double maxDistance) {
        return (player, entity) -> {
            if (entity.getLocation() == null || player.getLocation().getWorld() != entity.getLocation().getWorld()) {
                return false;
            }

            return player.getLocation().distanceSquared(entity.getLocation()) <= maxDistance * maxDistance;
        };
    }

    static VisibilityRule sameWorld() {
        return (player, entity) -> {
            if (entity.getLocation() == null) return false;
            return player.getLocation().getWorld() == entity.getLocation().getWorld();
        };
    }

    static VisibilityRule permission(String permission) {
        return (player, entity) -> player.hasPermission(permission);
    }

    static VisibilityRule lineOfSight() {
        return (player, entity) -> {
            if (entity.getLocation() == null) return false;
            return player.hasLineOfSight(entity.getLocation());
        };
    }

    static VisibilityRule custom(BiPredicate<Player, AbstractEntity> predicate) {
        return predicate::test;
    }

    default VisibilityRule and(VisibilityRule other) {
        return (player, entity) -> this.test(player, entity) && other.test(player, entity);
    }

    default VisibilityRule or(VisibilityRule other) {
        return (player, entity) -> this.test(player, entity) || other.test(player, entity);
    }

    default @NotNull VisibilityRule negate() {
        return (player, entity) -> !this.test(player, entity);
    }

}
