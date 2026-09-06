package org.klyx.exo.paper.meta;

import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaEntry;

import java.util.ArrayList;
import java.util.List;

public final class PaperMetaPackets {

    @SuppressWarnings("rawtypes")
    public static @Nullable ClientboundSetEntityDataPacket toPacket(int entityId, List<MetaEntry<?>> entries, int protocolVersion) {
        if (entries.isEmpty()) return null;

        List<SynchedEntityData.DataValue<?>> values = new ArrayList<>(entries.size());
        for (MetaEntry<?> entry : entries) {
            MetaAccessor.Resolved resolved = entry.accessor().resolve(protocolVersion);
            EntityDataSerializer serializer = PaperMetaSerializers.INSTANCE.serializerFor(resolved.type());

            Object nmsValue = PaperMetaSerializers.toNmsValue(resolved.type(), entry.value());
            values.add(SynchedEntityData.DataValue.create(new EntityDataAccessor(resolved.index(), serializer), nmsValue));
        }

        return new ClientboundSetEntityDataPacket(entityId, values);
    }

}
