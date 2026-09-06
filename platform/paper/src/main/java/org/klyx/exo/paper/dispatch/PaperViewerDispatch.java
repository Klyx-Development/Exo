package org.klyx.exo.paper.dispatch;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.dispatch.PacketCategory;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.entity.events.ViewerHideEntityEvent;
import org.klyx.exo.entity.events.ViewerShowEntityEvent;
import org.klyx.exo.entity.meta.impl.AbstractEntityMeta;
import org.klyx.exo.entity.meta.impl.MetaViewerGrouping;
import org.klyx.exo.paper.entity.PaperEntityPackets;
import org.klyx.exo.paper.meta.PaperMetaPackets;
import org.klyx.exo.common.ViewerDispatch;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class PaperViewerDispatch implements ViewerDispatch {

    private final PacketDispatcher<Packet<?>> packetDispatcher;

    public PaperViewerDispatch(PacketDispatcher<Packet<?>> packetDispatcher) {
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public boolean onViewerAdded(ExoEntity entity, UUID viewer, boolean wasChunkLoad) {
        List<Packet<?>> defaultPackets = new ArrayList<>();
        defaultPackets.add(PaperEntityPackets.createSpawnPacket(entity));

        AbstractEntityMeta meta = entity.entityMeta();
        if (meta != null) {
            int protocolVersion = MetaViewerGrouping.resolveVersion(viewer);
            ClientboundSetEntityDataPacket metaPacket = PaperMetaPackets.toPacket(entity.entityId(), meta.toEntries(), protocolVersion);
            if (metaPacket != null) defaultPackets.add(metaPacket);
        }

        ViewerShowEntityEvent<Packet<?>> event = new ViewerShowEntityEvent<>(defaultPackets, viewer, wasChunkLoad);
        entity.eventBus().post(event);
        if (event.isCancelled()) return false;

        packetDispatcher.schedule(viewer, PacketCategory.SPAWN, event.packets());
        return true;
    }

    @Override
    public boolean onViewerRemoved(ExoEntity entity, UUID viewer, boolean wasChunkUnload) {
        List<Packet<?>> defaultPackets = new ArrayList<>();
        defaultPackets.add(new ClientboundRemoveEntitiesPacket(entity.entityId()));

        ViewerHideEntityEvent<Packet<?>> event = new ViewerHideEntityEvent<>(defaultPackets, viewer, wasChunkUnload);
        entity.eventBus().post(event);
        if (event.isCancelled()) return false;

        if (!wasChunkUnload) {
            packetDispatcher.schedule(viewer, PacketCategory.DESTROY, event.packets());
        }
        return true;
    }
}
