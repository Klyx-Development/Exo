package org.klyx.exo.util;

import org.jetbrains.annotations.NotNull;

public class Key implements net.kyori.adventure.key.Key {

    private static final String DEFAULT_NAMESPACE = "minecraft";

    private final String namespace;
    private final String value;

    protected Key(String namespace, String value) {
        this.namespace = namespace;
        this.value = value;
    }

    public static Key of(String namespace, String value) {
        return new Key(namespace, value);
    }

    public static Key of(String key) {
        String[] split = key.split(":");
        if (split.length != 2) {
            return new Key(DEFAULT_NAMESPACE, key);
        }

        return new Key(split[0], split[1]);
    }

    @Override
    public @NotNull String namespace() {
        return namespace;
    }

    @Override
    public @NotNull String value() {
        return value;
    }

    @Override
    public @NotNull String asString() {
        return namespace + ':' + value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof net.kyori.adventure.key.Key adv
                && namespace.equals(adv.namespace()) && value.equals(adv.value());
    }

}
