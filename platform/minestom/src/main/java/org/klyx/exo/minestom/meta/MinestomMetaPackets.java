package org.klyx.exo.minestom.meta;

import net.minestom.server.entity.Metadata;
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MinestomMetaPackets {

    public static @Nullable EntityMetaDataPacket toPacket(int entityId, List<MetaEntry<?>> entries, int protocolVersion) {
        if (entries.isEmpty()) return null;

        Map<Integer, Metadata.Entry<?>> values = new HashMap<>(entries.size());
        for (MetaEntry<?> entry : entries) {
            MetaAccessor.Resolved resolved = entry.accessor().resolve(protocolVersion);
            values.put(resolved.index(), MinestomMetaSerializers.toEntry(resolved.type(), entry.value()));
        }
        return new EntityMetaDataPacket(entityId, values);
    }
}
