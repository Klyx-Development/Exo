package org.klyx.exo.entity.meta;

public enum ItemDisplayContext {
    NONE,
    THIRD_PERSON_LEFT_HAND,
    THIRD_PERSON_RIGHT_HAND,
    FIRST_PERSON_LEFT_HAND,
    FIRST_PERSON_RIGHT_HAND,
    HEAD,
    GUI,
    GROUND,
    FIXED,
    ON_SHELF;

    private static final ItemDisplayContext[] VALUES = values();

    public byte getId() {
        return (byte) ordinal();
    }

    public static ItemDisplayContext byId(int id) {
        return VALUES[id];
    }
}
