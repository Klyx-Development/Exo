package org.klyx.exo.entity.meta;

import org.klyx.exo.util.Key;

public record ExoVillagerData(Key type, Key profession, int level) {
    public static final ExoVillagerData DEFAULT = new ExoVillagerData(Key.of("plains"), Key.of("none"), 1);
}
