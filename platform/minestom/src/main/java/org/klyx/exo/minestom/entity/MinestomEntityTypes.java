package org.klyx.exo.minestom.entity;

import net.minestom.server.entity.EntityType;
import org.klyx.exo.entity.data.ExoEntityType;

public final class MinestomEntityTypes {

    private MinestomEntityTypes() {}

    public static EntityType toMinestom(ExoEntityType type) {
        EntityType entityType = EntityType.fromKey(type.key());
        if (entityType == null) throw new IllegalArgumentException("Unknown entity type: " + type.key());
        return entityType;
    }
}
