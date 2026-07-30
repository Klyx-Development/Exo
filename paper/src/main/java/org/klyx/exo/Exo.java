package org.klyx.exo;

import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.EntityManager;
import org.klyx.exo.util.packet.PacketManager;

public class Exo {

    private static boolean enabled = false;
    private static @Nullable JavaPlugin plugin;
    private static final EntityManager ENTITY_MANAGER = new EntityManager();
    private static final PacketManager PACKET_MANAGER = new PacketManager();

    public static @Nullable JavaPlugin plugin() {
        return plugin;
    }

    public static EntityManager entityManager() {
        return ENTITY_MANAGER;
    }

    public static void init(JavaPlugin plugin) {
        //noinspection ConstantConditions
        if (enabled) throw new IllegalStateException("Exo already initialized");

        Exo.plugin = plugin;
        enabled = true;
        PACKET_MANAGER.register();
    }

    public static void destroy() {
        if (!enabled) throw new IllegalStateException("Exo not initialized");

        ENTITY_MANAGER.destroy();
        PACKET_MANAGER.unregister();
        plugin = null;
        enabled = false;
    }

}
