package org.klyx.exo.paper.world;

import org.bukkit.World;
import org.klyx.exo.world.ExoWorld;

import java.util.UUID;

public final class ExoPaperWorld extends ExoWorld {

    private final World world;

    private ExoPaperWorld(World world) {
        this.world = world;
    }

    public static ExoPaperWorld of(World world) {
        return new ExoPaperWorld(world);
    }

    public World bukkit() {
        return world;
    }

    @Override
    public UUID uuid() {
        return world.getUID();
    }

    @Override
    public String name() {
        return world.getName();
    }
}
