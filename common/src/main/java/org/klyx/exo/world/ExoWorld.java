package org.klyx.exo.world;

import java.util.UUID;

public abstract class ExoWorld {

    public abstract UUID uuid();

    public abstract String name();

    @Override
    public final boolean equals(Object obj) {
        return obj instanceof ExoWorld other && uuid().equals(other.uuid());
    }

    @Override
    public final int hashCode() {
        return uuid().hashCode();
    }
}
