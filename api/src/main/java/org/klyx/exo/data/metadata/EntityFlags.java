package org.klyx.exo.data.metadata;

public enum EntityFlags {
    ON_FIRE(0),
    CROUCHING(1),
    SPRINTING(3),
    SWIMMING(4),
    INVISIBLE(5),
    GLOWING(6),
    ELYTRA_FLYING(7);

    private int value;

    EntityFlags(int value) {
        this.value = value;
    }

}
