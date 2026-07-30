package org.klyx.exo.entity.viewer;

import com.destroystokyo.paper.event.player.PlayerConnectionCloseEvent;
import io.papermc.paper.event.connection.configuration.PlayerConnectionInitialConfigureEvent;
import net.minecraft.network.Connection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.util.packet.impl.Packets;
import xyz.bitsquidd.bits.log.Logger;

import java.util.Objects;

public class BukkitViewerListeners implements Listener {

    public BukkitViewerListeners(JavaPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    protected void onPlayerConnectionInitialConfigure(PlayerConnectionInitialConfigureEvent event) {
        try {
            Connection connection = (Connection) Packets.INSTANCE.getConnectionField().get(event.getConnection());
            Packets.INSTANCE.registerConnection(Objects.requireNonNull(event.getConnection().getProfile().getId()), connection);
        } catch (Exception e) {
            Logger.warn("Failed to set up interceptor for player " + event.getConnection().getProfile().getName());
        }
    }

    @EventHandler
    protected void onPlayerConnectionClose(PlayerConnectionCloseEvent event) {
        try {
            Packets.INSTANCE.unregisterPlayer(event.getPlayerUniqueId(), true);
        } catch (Exception e) {
            Logger.warn("Failed to remove interceptor for player " + event.getPlayerName());
        }
    }

    @EventHandler
    protected void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        //noinspection ConstantConditions
        if (player == null) return;

        for (ExoEntity entity : Exo.entityManager().getEntities()) {
            if (entity.isViewer(player.getUniqueId())) {
                entity.getViewerManager().handleUnload(player);
            }
        }
    }

}
