package org.klyx.exo.paper.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.klyx.exo.paper.world.ExoPaperWorld;
import org.klyx.exo.player.ExoPlayer;
import org.klyx.exo.world.ExoWorld;

import java.util.UUID;

public final class ExoPaperPlayer extends ExoPlayer {

    private final Player player;

    private ExoPaperPlayer(Player player) {
        this.player = player;
    }

    public static ExoPaperPlayer of(Player player) {
        return new ExoPaperPlayer(player);
    }

    public static ExoPaperPlayer of(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);
        if (p == null) {
            throw new IllegalArgumentException("Player not found");
        }

        return new ExoPaperPlayer(p);
    }

    public Player bukkit() {
        return player;
    }

    @Override
    public UUID uuid() {
        return player.getUniqueId();
    }

    @Override
    public String name() {
        return player.getName();
    }

    @Override
    public ExoWorld world() {
        return ExoPaperWorld.of(player.getWorld());
    }

    @Override
    public int protocolVersion() {
        return player.getProtocolVersion();
    }

    @Override
    public int entityId() {
        return player.getEntityId();
    }
}
