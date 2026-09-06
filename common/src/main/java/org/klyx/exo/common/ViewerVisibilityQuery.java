package org.klyx.exo.common;

import org.jspecify.annotations.Nullable;
import org.klyx.exo.player.ExoPlayer;
import org.klyx.exo.world.ExoWorld;

import java.util.Collection;
import java.util.UUID;

public interface ViewerVisibilityQuery {
    Collection<ExoPlayer> playersSeeingChunk(ExoWorld world, int chunkX, int chunkZ);
    @Nullable ExoPlayer resolvePlayer(UUID uuid);
}
