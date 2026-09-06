package org.klyx.exo.minestom.dispatch;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.play.DestroyEntitiesPacket;
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;
import net.minestom.server.network.packet.server.play.SpawnEntityPacket;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.data.world.EntityWorldState;
import org.klyx.exo.entity.dispatch.PacketCategory;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.entity.events.ViewerHideEntityEvent;
import org.klyx.exo.entity.events.ViewerShowEntityEvent;
import org.klyx.exo.entity.meta.impl.AbstractEntityMeta;
import org.klyx.exo.entity.meta.impl.MetaViewerGrouping;
import org.klyx.exo.minestom.entity.MinestomEntityTypes;
import org.klyx.exo.minestom.meta.MinestomMetaPackets;
import org.klyx.exo.minestom.util.MinestomLocationHelper;
import org.klyx.exo.common.ViewerDispatch;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class MinestomViewerDispatch implements ViewerDispatch {

    private final PacketDispatcher<ServerPacket> packetDispatcher;

    public MinestomViewerDispatch(PacketDispatcher<ServerPacket> packetDispatcher) {
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public boolean onViewerAdded(ExoEntity entity, UUID viewer, boolean wasChunkLoad) {
        List<ServerPacket> defaultPackets = new ArrayList<>();
        defaultPackets.add(createSpawnPacket(entity));

        AbstractEntityMeta meta = entity.entityMeta();
        if (meta != null) {
            int protocolVersion = MetaViewerGrouping.resolveVersion(viewer);
            EntityMetaDataPacket metaPacket = MinestomMetaPackets.toPacket(entity.entityId(), meta.toEntries(), protocolVersion);
            if (metaPacket != null) defaultPackets.add(metaPacket);
        }

        ViewerShowEntityEvent<ServerPacket> event = new ViewerShowEntityEvent<>(defaultPackets, viewer, wasChunkLoad);
        entity.eventBus().post(event);
        if (event.isCancelled()) return false;

        packetDispatcher.schedule(viewer, PacketCategory.SPAWN, event.packets());
        return true;
    }

    @Override
    public boolean onViewerRemoved(ExoEntity entity, UUID viewer, boolean wasChunkUnload) {
        List<ServerPacket> defaultPackets = new ArrayList<>();
        defaultPackets.add(new DestroyEntitiesPacket(entity.entityId()));

        ViewerHideEntityEvent<ServerPacket> event = new ViewerHideEntityEvent<>(defaultPackets, viewer, wasChunkUnload);
        entity.eventBus().post(event);
        if (event.isCancelled()) return false;

        if (!wasChunkUnload) {
            packetDispatcher.schedule(viewer, PacketCategory.DESTROY, event.packets());
        }
        return true;
    }

    private static SpawnEntityPacket createSpawnPacket(ExoEntity entity) {
        EntityWorldState worldState = entity.getWorldStateManager().getWorldState();
        Pos pos = MinestomLocationHelper.toPos(worldState.asExoPos());
        Vec velocity = worldState.velocity() != null ? MinestomLocationHelper.toVec(worldState.velocity()) : Vec.ZERO;

        return new SpawnEntityPacket(
                entity.entityId(), entity.uuid(), MinestomEntityTypes.toMinestom(entity.entityType()),
                pos, worldState.currentVerticalHeadRot(), entity.objectDataValue(), velocity
        );
    }
}
