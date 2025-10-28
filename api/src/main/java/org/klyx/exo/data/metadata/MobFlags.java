package org.klyx.exo.data.metadata;

public enum MobFlags {
    NO_AI(1),
    LEFT_HANDED(2),
    AGGRESSIVE(3);

    private final int flag;

    MobFlags(int flag) {
        this.flag = flag;
    }
}
