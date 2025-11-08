package org.klyx.exo.data.metadata;

public enum BillboardConstraints {
    FIXED(0),
    VERTICAL(1),
    HORIZONTAL(2),
    CENTER(3)
    ;

    private int value;

    BillboardConstraints(int value) {
        this.value = value;
    }
}
