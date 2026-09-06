package org.klyx.exo.paper.entity.data.object.types;

import org.bukkit.entity.EntityType;
import org.klyx.exo.entity.data.object.AbstractObjectData;
import org.klyx.exo.paper.entity.PaperEntityTypes;

public class FireworkObjectData extends AbstractObjectData {
    protected FireworkObjectData(int owningEntityId) {
        super(PaperEntityTypes.toExo(EntityType.FIREWORK_ROCKET), owningEntityId);
    }

    public static FireworkObjectData of(int shooterEntityId) {
        return new FireworkObjectData(shooterEntityId);
    }

    public static FireworkObjectData none() {
        return new FireworkObjectData(0);
    }
}
