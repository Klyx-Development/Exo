package org.klyx.exo.entity.meta;

import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public record ExoProfile(@Nullable UUID id, @Nullable String name, List<ExoProfileProperty> properties) {

    public static final ExoProfile EMPTY = new ExoProfile(null, null, List.of());

    public static ExoProfile of(String name) {
        return new ExoProfile(null, name, List.of());
    }

    public static ExoProfile of(UUID id, String name) {
        return new ExoProfile(id, name, List.of());
    }
}
