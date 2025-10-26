package org.klyx.exo.visibility;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.klyx.exo.entities.impl.AbstractEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiPredicate;

public class VisibilityBuilder {
    private final int entityId;
    private final List<VisibilityRule> rules = new ArrayList<>();
    private @Nullable UUID targetPlayer = null;
    private boolean global = false;

    private VisibilityBuilder(int entityId) {
        this.entityId = entityId;
    }

    public static VisibilityBuilder forEntity(int entityId) {
        return new VisibilityBuilder(entityId);
    }

    public static VisibilityBuilder forEntity(@NotNull AbstractEntity entity) {
        return new VisibilityBuilder(entity.getEntityId());
    }

    public VisibilityBuilder targetPlayer(@NotNull UUID targetPlayer) {
        this.targetPlayer = targetPlayer;
        return this;
    }

    public VisibilityBuilder targetPlayer(@NotNull Player targetPlayer) {
        this.targetPlayer = targetPlayer.getUniqueId();
        return this;
    }

    public VisibilityBuilder global() {
        this.global = true;
        return this;
    }

    public VisibilityBuilder always() {
        rules.add(VisibilityRule.always());
        return this;
    }

    public VisibilityBuilder distance(double maxDistance) {
        rules.add(VisibilityRule.distance(maxDistance));
        return this;
    }

    public VisibilityBuilder sameWorld() {
        rules.add(VisibilityRule.sameWorld());
        return this;
    }

    public VisibilityBuilder permission(String permission) {
        rules.add(VisibilityRule.permission(permission));
        return this;
    }

    public VisibilityBuilder custom(BiPredicate<Player, AbstractEntity> predicate) {
        rules.add(VisibilityRule.custom(predicate));
        return this;
    }

    public void apply() {
        VisibilityManager visibilityManager = VisibilityManager.getInstance();
        VisibilityRule combinedRule = rules.stream()
                .reduce(VisibilityRule::and)
                .orElse((player, entity) -> true);

        if (global) {
            visibilityManager.addEntityCondition(entityId, combinedRule);
        } else if (targetPlayer != null) {
            visibilityManager.addCondition(entityId, targetPlayer, combinedRule);
        } else {
            throw new IllegalStateException("Must specify a target player or use global()");
        }
    }
}
