package org.klyx.exo.visibility;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.klyx.exo.data.entity.EntityState;
import org.klyx.exo.entities.base.AbstractEntity;
import org.klyx.exo.storage.EntityStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VisibilityManager {
    private static final VisibilityManager INSTANCE = new VisibilityManager();

    private final Map<Integer, Map<UUID, ConditionChain>> entityConditions = new ConcurrentHashMap<>();
    private final List<VisibilityRule> globalRules = new ArrayList<>();

    private @Nullable BukkitTask updateTask;
    private boolean updateTaskEnabled = false;
    private long updateInterval = 1L;

    private VisibilityManager() {}

    public static VisibilityManager getInstance() {
        return INSTANCE;
    }

    public void startAutoUpdate(@NotNull JavaPlugin plugin, long intervalTicks, boolean async) {
        if (updateTaskEnabled) stopAutoUpdate();

        this.updateInterval = intervalTicks;
        this.updateTaskEnabled = true;

        if (async) {
            updateTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::updateAllConditions, 0L, intervalTicks);
        } else {
            updateTask = Bukkit.getScheduler().runTaskTimer(plugin, this::updateAllConditions, 0L, intervalTicks);
        }
    }

    public void startAutoUpdate(@NotNull JavaPlugin plugin) {
        startAutoUpdate(plugin, 1L, true);
    }

    public void stopAutoUpdate() {
        if (updateTask != null) {
            updateTask.cancel();
            updateTask = null;
        }
        updateTaskEnabled = false;
    }

    private void updateAllConditions() {
        if (entityConditions.isEmpty()) return;

        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        if (onlinePlayers.isEmpty()) return;

        for (Integer entityId : entityConditions.keySet()) {
            AbstractEntity entity = EntityStorage.getEntity(entityId);
            if (entity == null || entity.getState() != EntityState.ALIVE) continue;

            updateEntity(entity, onlinePlayers);
        }
    }

    public void addCondition(int entityId, @NotNull UUID playerUUID, @NotNull VisibilityRule rule) {
        entityConditions.computeIfAbsent(entityId, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(playerUUID, k -> new ConditionChain())
                .addRule(rule);
    }

    public void addEntityCondition(int entityId, @NotNull VisibilityRule rule) {
        entityConditions.computeIfAbsent(entityId, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(ConditionChain.ALL_PLAYERS_KEY, k -> new ConditionChain())
                .addRule(rule);
    }

    public void addGlobalRule(@NotNull VisibilityRule rule) {
        globalRules.add(rule);
    }

    public void removeCondition(int entityId, @NotNull UUID playerUUID, @NotNull VisibilityRule rule) {
        Map<UUID, ConditionChain> playerConditions = entityConditions.get(entityId);
        if (playerConditions != null) {
            ConditionChain chain = playerConditions.get(playerUUID);
            if (chain != null) {
                chain.removeRule(rule);
                if (chain.isEmpty()) {
                    playerConditions.remove(playerUUID);
                }
            }
            if (playerConditions.isEmpty()) {
                entityConditions.remove(entityId);
            }
        };
    }

    public void clearPlayerConditions(int entityId, @NotNull UUID playerUUID) {
        Map<UUID, ConditionChain> playerConditions = entityConditions.get(entityId);
        if (playerConditions != null) {
            playerConditions.remove(playerUUID);
            if (playerConditions.isEmpty()) {
                entityConditions.remove(entityId);
            }
        }
    }

    public void clearEntityConditions(int entityId) {
        entityConditions.remove(entityId);
    }

    public void clearPlayerFromAll(@NotNull UUID playerUUID) {
        entityConditions.values().forEach(map -> map.remove(playerUUID));
    }

    public void updateEntity(@NotNull AbstractEntity entity, @NotNull Collection<? extends Player> players) {
        if (!entity.isAlive()) return;

        for (Player player : players) {
            boolean shouldSee = canSee(player, entity);
            boolean isAlreadyViewing = entity.hasViewer(player);

            if (shouldSee && !isAlreadyViewing) {
                entity.showTo(player);
            } else if (!shouldSee && isAlreadyViewing) {
                entity.hideFrom(player);
            }
        }
    }

    public void updatePlayer(@NotNull Player player, @NotNull Collection<AbstractEntity> entities) {
        if (!player.isOnline()) return;

        for (AbstractEntity entity : entities) {
            if (!entity.isAlive()) continue;

            boolean shouldSee = canSee(player, entity);
            boolean isViewing = entity.hasViewer(player);

            if (shouldSee && !isViewing) {
                entity.showTo(player);
            } else if (!shouldSee && isViewing) {
                entity.hideFrom(player);
            }
        }
    }

    public boolean canSee(@NotNull Player player, @NotNull AbstractEntity entity) {
        int entityId = entity.getEntityId();
        UUID playerUUID = player.getUniqueId();

        for (VisibilityRule rule : globalRules) {
            if (!rule.test(player, entity)) {
                return false;
            }
        }

        Map<UUID, ConditionChain> playerConditions = entityConditions.get(entityId);
        if (playerConditions == null) return true;

        ConditionChain allPlayerConditions = playerConditions.get(ConditionChain.ALL_PLAYERS_KEY);
        if (allPlayerConditions != null && !allPlayerConditions.test(player, entity)) {
            return false;
        }

        ConditionChain specificConditions = playerConditions.get(playerUUID);
        if (specificConditions != null) {
            return specificConditions.test(player, entity);
        }

        return true;
    }

    public void forceUpdateAll() {
        Collection<AbstractEntity> entities = EntityStorage.getEntities();
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();

        for (AbstractEntity entity : entities) {
            if (entity.isAlive()) {
                updateEntity(entity, players);
            }
        }
    }

    public boolean hasConditions(int entityId) {
        return entityConditions.containsKey(entityId);
    }

    public int getConditionCount(int entityId) {
        Map<UUID, ConditionChain> playerConditions = entityConditions.get(entityId);
        return playerConditions != null ? playerConditions.size() : 0;
    }

    public int getEntityConditionCount() {
        return entityConditions.size();
    }

    public int getGlobalRuleCount() {
        return globalRules.size();
    }
}
