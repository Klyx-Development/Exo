package org.klyx.exo.entity.data.attribute;

import org.klyx.exo.util.Key;

public record ExoAttributeModifier(Key key, double amount, Operation operation) {
    public enum Operation {
        ADD_VALUE,
        ADD_MULTIPLIED_BASE,
        ADD_MULTIPLIED_TOTAL
    }
}
