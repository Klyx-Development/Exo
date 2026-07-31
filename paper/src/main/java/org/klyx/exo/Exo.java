package org.klyx.exo;

import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.EntityManager;
import org.klyx.exo.entity.viewer.BukkitViewerListeners;
import org.klyx.exo.util.packet.PacketManager;
import org.slf4j.LoggerFactory;
import xyz.bitsquidd.bits.Bits;
import xyz.bitsquidd.bits.log.BasicLogger;
import xyz.bitsquidd.bits.log.Logger;

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
        Bits.generic("Exo");
        new BasicLogger(LoggerFactory.getLogger("Exo"), Logger.LogFlags.defaultFlags());

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
