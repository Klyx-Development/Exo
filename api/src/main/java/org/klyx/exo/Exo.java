package org.klyx.exo;

import org.bukkit.plugin.java.JavaPlugin;
import org.klyx.exo.conditions.EntityConditionManager;

public class Exo {

    private static JavaPlugin pluginInstance;

    public static void init(JavaPlugin javaPlugin) {
        pluginInstance = javaPlugin;
        javaPlugin.getServer().getScheduler().runTaskTimerAsynchronously(javaPlugin, EntityConditionManager::updateAllConditions, 1L, 1L);
    }

}
