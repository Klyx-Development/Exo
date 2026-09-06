package org.klyx.exo.entity.data.object;

import org.klyx.exo.entity.data.ExoEntityType;

public abstract class AbstractObjectData {

    private final ExoEntityType boundType;
    private final int value;

    protected AbstractObjectData(ExoEntityType boundType, int value) {
        this.boundType = boundType;
        this.value = value;
    }

    public ExoEntityType boundType() {
        return boundType;
    }

    public int value() {
        return value;
    }

    public static AbstractObjectData raw(ExoEntityType type, int value) {
        return new AbstractObjectData(type, value) {};
    }

}
