package org.klyx.exo.data.keys;

import net.minecraft.network.syncher.EntityDataAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DataKey<T> {

    private final int index;
    private final EntityDataAccessor<T> accessor;
    private final T defaultValue;

    private DataKey(int index, @NotNull EntityDataAccessor<T> accessor, @Nullable T defaultValue) {
        this.index = index;
        this.accessor = accessor;
        this.defaultValue = defaultValue;
    }

    public static <T> DataKey<T> create(int index, @NotNull EntityDataAccessor<T> accessor, @NotNull T defaultValue) {
        return new DataKey<>(index, accessor, defaultValue);
    }

    public static <T> DataKey<T> create(int index, @NotNull EntityDataAccessor<T> accessor) {
        return new DataKey<>(index, accessor, null);
    }

    public int getIndex() {
        return index;
    }

    public @NotNull EntityDataAccessor<T> getAccessor() {
        return accessor;
    }

    public @Nullable T getDefaultValue() {
        return defaultValue;
    }

}
