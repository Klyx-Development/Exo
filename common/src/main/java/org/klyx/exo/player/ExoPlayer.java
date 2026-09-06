package org.klyx.exo.player;

import org.klyx.exo.world.ExoWorld;

import java.util.UUID;

public abstract class ExoPlayer {

    public abstract UUID uuid();
    public abstract String name();
    public abstract ExoWorld world();
    public abstract int protocolVersion();
    public abstract int entityId();

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof ExoPlayer other && uuid().equals(other.uuid());
    }

    @Override
    public final int hashCode() {
        return uuid().hashCode();
    }
}
