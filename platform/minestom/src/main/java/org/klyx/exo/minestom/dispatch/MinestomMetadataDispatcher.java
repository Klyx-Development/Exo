package org.klyx.exo.minestom.dispatch;

import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.dispatch.PacketCategory;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.entity.meta.impl.MetaEntry;
import org.klyx.exo.entity.meta.impl.MetaViewerGrouping;
import org.klyx.exo.minestom.meta.MinestomMetaPackets;
import org.klyx.exo.common.MetadataDispatcher;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class MinestomMetadataDispatcher implements MetadataDispatcher {

    private final PacketDispatcher<ServerPacket> packetDispatcher;

    public MinestomMetadataDispatcher(PacketDispatcher<ServerPacket> packetDispatcher) {
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public void dispatchFullSync(ExoEntity entity, UUID viewer, List<MetaEntry<?>> entries) {
        int protocolVersion = MetaViewerGrouping.resolveVersion(viewer);
        EntityMetaDataPacket packet = MinestomMetaPackets.toPacket(entity.entityId(), entries, protocolVersion);
        if (packet == null) return;
        packetDispatcher.schedule(viewer, PacketCategory.SPAWN, List.of(packet));
    }

    @Override
    public void dispatchDirty(ExoEntity entity, List<MetaEntry<?>> dirtyEntries) {
        Map<Integer, List<UUID>> byVersion = MetaViewerGrouping.groupByProtocolVersion(entity.getActiveViewers());
        for (Map.Entry<Integer, List<UUID>> group : byVersion.entrySet()) {
            EntityMetaDataPacket packet = MinestomMetaPackets.toPacket(entity.entityId(), dirtyEntries, group.getKey());
            if (packet == null) continue;
            for (UUID viewer : group.getValue()) {
                packetDispatcher.schedule(viewer, PacketCategory.DEFAULT, List.of(packet));
            }
        }
    }
}
