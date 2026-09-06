package org.klyx.exo.common;

import org.klyx.exo.player.ExoPlayer;

public interface SpectateDispatcher {
    void dispatchSpectate(ExoPlayer spectator, int entityIdToSpectate);
}
