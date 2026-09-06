package org.klyx.exo.minestom.player;

import net.minestom.server.entity.Player;
import org.klyx.exo.minestom.world.MinestomExoWorld;
import org.klyx.exo.player.ExoPlayer;
import org.klyx.exo.world.ExoWorld;

import java.util.UUID;

public final class MinestomExoPlayer extends ExoPlayer {

    private final Player player;

    private MinestomExoPlayer(Player player) {
        this.player = player;
    }

    public static MinestomExoPlayer of(Player player) {
        return new MinestomExoPlayer(player);
    }

    public Player minestom() {
        return player;
    }

    @Override
    public UUID uuid() {
        return player.getUuid();
    }

    @Override
    public String name() {
        return player.getUsername();
    }

    @Override
    public ExoWorld world() {
        return MinestomExoWorld.of(player.getInstance());
    }

    @Override
    public int protocolVersion() {
        return player.getPlayerConnection().getProtocolVersion();
    }

    @Override
    public int entityId() {
        return player.getEntityId();
    }
}
