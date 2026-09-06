package org.klyx.exo.paper.dispatch;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.dispatch.PacketCategory;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.entity.meta.impl.MetaEntry;
import org.klyx.exo.entity.meta.impl.MetaViewerGrouping;
import org.klyx.exo.paper.meta.PaperMetaPackets;
import org.klyx.exo.common.MetadataDispatcher;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class PaperMetadataDispatcher implements MetadataDispatcher {

    private final PacketDispatcher<Packet<?>> packetDispatcher;

    public PaperMetadataDispatcher(PacketDispatcher<Packet<?>> packetDispatcher) {
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public void dispatchFullSync(ExoEntity entity, UUID viewer, List<MetaEntry<?>> entries) {
        int protocolVersion = MetaViewerGrouping.resolveVersion(viewer);
        ClientboundSetEntityDataPacket packet = PaperMetaPackets.toPacket(entity.entityId(), entries, protocolVersion);
        if (packet == null) return;
        packetDispatcher.schedule(viewer, PacketCategory.SPAWN, List.of(packet));
    }

    @Override
    public void dispatchDirty(ExoEntity entity, List<MetaEntry<?>> dirtyEntries) {
        Map<Integer, List<UUID>> byVersion = MetaViewerGrouping.groupByProtocolVersion(entity.getActiveViewers());
        for (Map.Entry<Integer, List<UUID>> group : byVersion.entrySet()) {
            ClientboundSetEntityDataPacket packet = PaperMetaPackets.toPacket(entity.entityId(), dirtyEntries, group.getKey());
            if (packet == null) continue;
            for (UUID viewer : group.getValue()) {
                packetDispatcher.schedule(viewer, PacketCategory.DEFAULT, List.of(packet));
            }
        }
    }
}
