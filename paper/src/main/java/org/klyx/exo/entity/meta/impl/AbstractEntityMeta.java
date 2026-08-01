package org.klyx.exo.entity.meta.impl;

import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractEntityMeta {

    private final Map<MetaAccessor<?>, Object> values = new HashMap<>();

    protected <T> void set(MetaAccessor<T> accessor, T value) {
        values.put(accessor, value);
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
    public List<SynchedEntityData.DataValue<?>> toPacketData() {
        List<SynchedEntityData.DataValue<?>> resolved = new ArrayList<>(values.size());
        for (Map.Entry<MetaAccessor<?>, Object> entry : values.entrySet()) {
            MetaAccessor accessor = entry.getKey();
            resolved.add(SynchedEntityData.DataValue.create(
                    new EntityDataAccessor<>(accessor.index(), accessor.serializer()),
                    entry.getValue()));
        }
        return resolved;
    }

    public ClientboundSetEntityDataPacket createPacket(int entityId) {
        return new ClientboundSetEntityDataPacket(entityId, toPacketData());
    }
}