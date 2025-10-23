package org.klyx.exo;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.klyx.exo.visibility.VisibilityManager;

public class Exo {

    private static JavaPlugin pluginInstance;
    private static boolean initialized = false;

    public static void init(@NotNull JavaPlugin javaPlugin) {
        if (initialized) throw new IllegalStateException("Exo already initialized");

        pluginInstance = javaPlugin;
        initialized = true;

        VisibilityManager.getInstance().startAutoUpdate(pluginInstance);
    }

    public static void init(@NotNull JavaPlugin plugin, long updateIntervalTicks, boolean async) {
        if (initialized) throw new IllegalStateException("Exo already initialized");

        pluginInstance = plugin;
        initialized = true;

        VisibilityManager.getInstance().startAutoUpdate(pluginInstance, updateIntervalTicks, async);
    }

    public static void shutdown() {
        if (!initialized) return;

        VisibilityManager.getInstance().stopAutoUpdate();
        initialized = false;
        pluginInstance = null;
    }

}
