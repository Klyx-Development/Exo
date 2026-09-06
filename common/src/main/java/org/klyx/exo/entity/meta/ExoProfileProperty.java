package org.klyx.exo.entity.meta;

import org.jspecify.annotations.Nullable;

public record ExoProfileProperty(String name, String value, @Nullable String signature) {
    public ExoProfileProperty(String name, String value) {
        this(name, value, null);
    }
}
