package org.klyx.exo;

import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.EntityManager;
import org.klyx.exo.entity.viewer.BukkitViewerListeners;
import org.klyx.exo.util.packet.PacketManager;
import org.slf4j.Logger;

public class Exo {

    private static boolean enabled = false;
    private static @Nullable JavaPlugin plugin;
    private static @Nullable Logger logger;
    private static final EntityManager ENTITY_MANAGER = new EntityManager();
    private static final PacketManager PACKET_MANAGER = new PacketManager();

    public static @Nullable JavaPlugin plugin() {
        return plugin;
    }

    public static Logger logger() {
        if (logger == null) throw new IllegalStateException("Exo not initialized");
        return logger;
    }

    public static EntityManager entityManager() {
        return ENTITY_MANAGER;
    }

    public static void init(JavaPlugin plugin) {
        //noinspection ConstantConditions
        if (enabled) throw new IllegalStateException("Exo already initialized");

        Exo.plugin = plugin;
        Exo.logger = plugin.getSLF4JLogger();
        enabled = true;
        PACKET_MANAGER.register();

        new BukkitViewerListeners(plugin);
    }

    public static void destroy() {
        if (!enabled) throw new IllegalStateException("Exo not initialized");

        ENTITY_MANAGER.destroy();
        PACKET_MANAGER.unregister();
        plugin = null;
        enabled = false;
    }

}
