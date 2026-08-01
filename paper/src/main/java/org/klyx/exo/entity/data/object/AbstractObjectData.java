package org.klyx.exo.entity.data.object;

import org.bukkit.entity.EntityType;

public abstract class AbstractObjectData {

    private final EntityType boundType;
    private final int value;

    protected AbstractObjectData(EntityType boundType, int value) {
        this.boundType = boundType;
        this.value = value;
    }

    public EntityType boundType() {
        return boundType;
    }

    public int value() {
        return value;
    }

    public static AbstractObjectData raw(EntityType type, int value) {
        return new AbstractObjectData(type, value) {};
    }

}
