package org.klyx.exo.minestom.world;

import net.minestom.server.instance.Instance;
import org.klyx.exo.world.ExoWorld;

import java.util.UUID;

public final class MinestomExoWorld extends ExoWorld {

    private final Instance instance;

    private MinestomExoWorld(Instance instance) {
        this.instance = instance;
    }

    public static MinestomExoWorld of(Instance instance) {
        return new MinestomExoWorld(instance);
    }

    public Instance instance() {
        return instance;
    }

    @Override
    public UUID uuid() {
        return instance.getUuid();
    }

    @Override
    public String name() {
        return instance.getUuid().toString();
    }
}
