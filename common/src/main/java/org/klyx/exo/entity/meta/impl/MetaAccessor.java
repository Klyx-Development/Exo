package org.klyx.exo.entity.meta.impl;

import org.klyx.exo.util.Buildable;

import java.util.ArrayList;
import java.util.List;

public final class MetaAccessor<T> {

    public static final int LATEST = Integer.MAX_VALUE;

    private final int index;
    private final MetaType type;
    private final T defaultValue;
    private final List<VersionOverride> overrides;

    public MetaAccessor(int index, MetaType type, T defaultValue) {
        this(index, type, defaultValue, List.of());
    }

    private MetaAccessor(int index, MetaType type, T defaultValue, List<VersionOverride> overrides) {
        this.index = index;
        this.type = type;
        this.defaultValue = defaultValue;
        this.overrides = overrides;
    }

    public static <T> Builder<T> builder(MetaType type, T defaultValue) {
        return new Builder<>(type, defaultValue);
    }

    public int index() {
        return index;
    }

    public MetaType type() {
        return type;
    }

    public T defaultValue() {
        return defaultValue;
    }

    /**
     * Resolves the index/type this accessor should use for a protocol version, and falls back
     * to the base index if no range covers it
     */
    public Resolved resolve(int protocolVersion) {
        for (VersionOverride override : overrides) {
            if (override.range().contains(protocolVersion)) {
                return new Resolved(override.index(), override.type());
            }
        }
        return new Resolved(index, type);
    }

    public record Resolved(int index, MetaType type) { }
    private record VersionOverride(ProtocolRange range, int index, MetaType type) { }

    public static final class Builder<T> implements Buildable<MetaAccessor<T>> {

        private final MetaType baseType;
        private final T defaultValue;
        private final List<VersionOverride> overrides = new ArrayList<>();
        private int index = -1;

        private Builder(MetaType baseType, T defaultValue) {
            this.baseType = baseType;
            this.defaultValue = defaultValue;
        }

        /**
         * The base/fallback index, used when no {@link #forVersions} range matches
         */
        public Builder<T> index(int index) {
            this.index = index;
            return this;
        }

        public Builder<T> forVersions(ProtocolRange range, int index) {
            return forVersions(range, index, baseType);
        }

        public Builder<T> forVersions(ProtocolRange range, int index, MetaType type) {
            overrides.add(new VersionOverride(range, index, type));
            return this;
        }

        @Override
        public MetaAccessor<T> build() {
            if (index == -1) {
                throw new IllegalStateException("This builder requires index()");
            }
            for (int i = 0; i < overrides.size(); i++) {
                ProtocolRange a = overrides.get(i).range();
                for (int j = i + 1; j < overrides.size(); j++) {
                    ProtocolRange b = overrides.get(j).range();
                    if (a.overlaps(b)) {
                        throw new IllegalStateException("Overriding version ranges: " + a + " and " + b);
                    }
                }
            }
            return new MetaAccessor<>(index, baseType, defaultValue, List.copyOf(overrides));
        }
    }
}
