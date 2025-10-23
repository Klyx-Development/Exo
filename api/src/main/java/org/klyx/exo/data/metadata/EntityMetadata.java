package org.klyx.exo.data.metadata;

import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.klyx.exo.data.keys.DataKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class EntityMetadata {

    private final int entityId;
    private final ConcurrentHashMap<Integer, SynchedEntityData.DataItem<?>> metadata = new ConcurrentHashMap<>();

    public EntityMetadata(int entityId) {
        this.entityId = entityId;
    }

    public <T> void set(@NotNull DataKey<T> key, @NotNull T value) {
        setIndex(key.getIndex(), key.getAccessor(), value);
    }

    public <T> void setIndex(int index, @NotNull EntityDataAccessor<T> accessor, @NotNull T value) {
        if (accessor.id() != index) {
            throw new IllegalArgumentException("Index mismatch: " + index + " != " + accessor.id());
        }

        SynchedEntityData.DataItem<?> current = metadata.get(index);
        if (current != null && Objects.equals(current.getValue(), value)) {
            return;
        }

        SynchedEntityData.DataItem<T> entry = new SynchedEntityData.DataItem<>(accessor, value);
        metadata.put(index, entry);
    }

    public <T> @Nullable T get(@NotNull DataKey<T> key) {
        T value = getIndex(key.getIndex());
        return value != null ? value : key.getDefaultValue();
    }

    public <T> @Nullable T getIndex(int index) {
        SynchedEntityData.DataItem<?> value = metadata.get(index);
        return value != null ? (T) value.getValue() : null;
    }

    public boolean has(@NotNull DataKey<?> key) {
        return metadata.containsKey(key.getIndex());
    }

    public void remove(@NotNull DataKey<?> key) {
        metadata.remove(key.getIndex());
    }

    public void cleanup() {
        metadata.clear();
    }

    public ClientboundSetEntityDataPacket createPacket() {
        List<SynchedEntityData.DataValue<?>> dataValues = getDataValues();
        if (dataValues.isEmpty()) throw new IllegalStateException("No data values to send.");

        try {
            return new ClientboundSetEntityDataPacket(entityId, dataValues);
        } catch (Exception e) {
            throw new IllegalStateException("Something went wrong trying to create a metadata packet for entity with id: " + entityId + ", " + e.getMessage());
        }
    }

    public List<SynchedEntityData.DataValue<?>> getDataValues() {
        List<SynchedEntityData.DataValue<?>> values = new ArrayList<>(metadata.size());

        for (SynchedEntityData.DataItem<?> item : metadata.values()) {
            try  {
                values.add(item.value());
            } catch (Exception e) {
                throw new IllegalStateException("Something went wrong trying to gather data values: " + e.getMessage());
            }
        }

        return values;
    }
}
