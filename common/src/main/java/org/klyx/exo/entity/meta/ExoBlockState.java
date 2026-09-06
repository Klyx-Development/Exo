package org.klyx.exo.entity.meta;

import org.klyx.exo.util.Key;

import java.util.Map;

public record ExoBlockState(Key block, Map<String, String> properties) {
    public static final ExoBlockState AIR = new ExoBlockState(Key.of("air"), Map.of());

    public static ExoBlockState of(Key block) {
        return new ExoBlockState(block, Map.of());
    }
}
