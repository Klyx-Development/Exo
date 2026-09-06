package org.klyx.exo.minestom.dispatch;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.minestom.player.MinestomExoPlayer;
import org.klyx.exo.minestom.world.MinestomExoWorld;
import org.klyx.exo.player.ExoPlayer;
import org.klyx.exo.common.ViewerVisibilityQuery;
import org.klyx.exo.world.ExoWorld;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class MinestomViewerVisibilityQuery implements ViewerVisibilityQuery {

    @Override
    public Collection<ExoPlayer> playersSeeingChunk(ExoWorld world, int chunkX, int chunkZ) {
        Instance instance = ((MinestomExoWorld) world).instance();
        Chunk chunk = instance.getChunkAt(chunkX << 4, chunkZ << 4);
        if (chunk == null) return List.of();

        return chunk.getViewers().stream().<ExoPlayer>map(MinestomExoPlayer::of).collect(Collectors.toList());
    }

    @Override
    public @Nullable ExoPlayer resolvePlayer(UUID uuid) {
        for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
            if (player.getUuid().equals(uuid)) return MinestomExoPlayer.of(player);
        }
        return null;
    }
}
