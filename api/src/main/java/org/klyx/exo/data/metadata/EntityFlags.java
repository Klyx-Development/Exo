package org.klyx.exo.data.metadata;

import org.klyx.exo.data.metadata.flag.FlagEnum;

public enum EntityFlags implements FlagEnum {
    ON_FIRE(0),
    CROUCHING(1),
    SPRINTING(3),
    SWIMMING(4),
    INVISIBLE(5),
    GLOWING(6),
    ELYTRA_FLYING(7);

    private final int bitPosition;

    EntityFlags(int bitPosition) {
        this.bitPosition = bitPosition;
    }

    @Override
    public int getBitPosition() {
        return bitPosition;
    }
}
