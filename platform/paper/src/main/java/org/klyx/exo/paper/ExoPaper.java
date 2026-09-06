package org.klyx.exo.paper;

import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.paper.entity.viewer.BukkitViewerListeners;
import org.klyx.exo.paper.platform.ExoPaperPlatform;
import org.klyx.exo.paper.packet.PacketManager;
import org.slf4j.Logger;

public final class ExoPaper {

    private static @Nullable JavaPlugin plugin;
    private static final PacketManager PACKET_MANAGER = new PacketManager();

    public static void init(JavaPlugin plugin) {
        if (ExoPaper.plugin != null) throw new IllegalStateException("Exo already initialized");
        ExoPaper.plugin = plugin;

        ExoPaperPlatform platform = new ExoPaperPlatform(plugin);
        Exo.init(platform);

        PACKET_MANAGER.register();
        platform.start();

        new BukkitViewerListeners(plugin);
    }

    public static void destroy() {
        if (ExoPaper.plugin == null) throw new IllegalStateException("Exo not initialized");

        ExoPaperPlatform platform = (ExoPaperPlatform) Exo.platform();
        platform.stop();
        PACKET_MANAGER.unregister();

        Exo.destroy();
        plugin = null;
    }

    public static JavaPlugin plugin() {
        if (plugin == null) throw new IllegalStateException("Exo not initialized");
        return plugin;
    }

    public static Logger logger() {
        return Exo.logger();
    }

}
