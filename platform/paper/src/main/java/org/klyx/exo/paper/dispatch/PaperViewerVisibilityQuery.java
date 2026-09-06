package org.klyx.exo.paper.dispatch;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.paper.player.ExoPaperPlayer;
import org.klyx.exo.paper.util.PaperLocUtil;
import org.klyx.exo.player.ExoPlayer;
import org.klyx.exo.common.ViewerVisibilityQuery;
import org.klyx.exo.world.ExoWorld;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class PaperViewerVisibilityQuery implements ViewerVisibilityQuery {

    @Override
    public Collection<ExoPlayer> playersSeeingChunk(ExoWorld world, int chunkX, int chunkZ) {
        World bukkitWorld = PaperLocUtil.toBukkitWorld(world);

        Collection<Player> players = bukkitWorld.getPlayersSeeingChunk(chunkX, chunkZ);
        if (players.isEmpty()) return List.of();

        return players.stream().map(ExoPaperPlayer::of).collect(Collectors.toList()); // mutable list
    }

    @Override
    public @Nullable ExoPlayer resolvePlayer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return player != null ? ExoPaperPlayer.of(player) : null;
    }
}
