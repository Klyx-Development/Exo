package org.klyx.exo.entity.meta.impl;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractEntityMeta {

    private final Map<MetaAccessor<?>, Object> values = new HashMap<>();
    private @Nullable Map<MetaAccessor<?>, Object> lastSyncedValues = null;
    private final Set<MetaAccessor<?>> forcedDirty = new HashSet<>();

    protected <T> void set(MetaAccessor<T> accessor, T value) {
        values.put(accessor, value);
    }

    protected <T> void forceDirty(MetaAccessor<T> accessor) {
        forcedDirty.add(accessor);
    }

    protected <T> T get(MetaAccessor<T> accessor) {
        Object value = values.get(accessor);
        return value != null ? (T) value : accessor.defaultValue();
    }

    protected boolean getFlag(MetaAccessor<Byte> accessor, int bit) {
        byte value = get(accessor);
        return (value & (1 << bit)) != 0;
    }

    protected void setFlag(MetaAccessor<Byte> accessor, int bit, boolean flag) {
        byte current = get(accessor);
        byte updated = (byte) (flag ? (current | (1 << bit)) : (current & ~(1 << bit)));
        set(accessor, updated);
    }

    @SuppressWarnings("rawtypes")
    public List<MetaEntry<?>> toEntries() {
        List<MetaEntry<?>> resolved = new ArrayList<>(values.size());
        for (Map.Entry<MetaAccessor<?>, Object> entry : values.entrySet()) {
            MetaAccessor accessor = entry.getKey();
            resolved.add(new MetaEntry<>(accessor, entry.getValue()));
        }
        return resolved;
    }

    public boolean needsFullSync() {
        return this.lastSyncedValues == null;
    }

    public boolean hasChanged() {
        if (needsFullSync()) return true;
        if (!forcedDirty.isEmpty()) return true;
        if (this.values.size() != this.lastSyncedValues.size()) return true;

        for (Map.Entry<MetaAccessor<?>, Object> entry : this.values.entrySet()) {
            if (!entry.getValue().equals(this.lastSyncedValues.get(entry.getKey()))) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    public List<MetaEntry<?>> dirtyEntries() {
        if (needsFullSync()) {
            return toEntries();
        }

        List<MetaEntry<?>> dirty = new ArrayList<>();
        for (Map.Entry<MetaAccessor<?>, Object> entry : this.values.entrySet()) {
            MetaAccessor accessor = entry.getKey();
            Object current = entry.getValue();
            if (forcedDirty.contains(accessor) || !current.equals(this.lastSyncedValues.get(accessor))) {
                dirty.add(new MetaEntry<>(accessor, current));
            }
        }
        return dirty;
    }

    public void markSynced() {
        this.lastSyncedValues = new HashMap<>(this.values);
        this.forcedDirty.clear();
    }
}
