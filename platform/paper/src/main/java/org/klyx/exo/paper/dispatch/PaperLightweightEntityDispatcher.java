package org.klyx.exo.paper.dispatch;

import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.common.LightweightEntityDispatcher;
import org.klyx.exo.entity.ExoPos;
import org.klyx.exo.entity.LightweightExoEntity;
import org.klyx.exo.entity.meta.impl.MetaEntry;
import org.klyx.exo.entity.meta.impl.MetaViewerGrouping;
import org.klyx.exo.paper.entity.PaperEntityTypes;
import org.klyx.exo.paper.meta.PaperMetaPackets;
import org.klyx.exo.paper.packet.impl.Packets;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class PaperLightweightEntityDispatcher implements LightweightEntityDispatcher {

    @Override
    public void spawn(LightweightExoEntity entity, ExoPos pos, Collection<UUID> viewers) {
        if (viewers.isEmpty()) return;

        List<MetaEntry<?>> entries = entity.meta().toEntries();
        for (Map.Entry<Integer, List<UUID>> group : MetaViewerGrouping.groupByProtocolVersion(viewers).entrySet()) {
            ClientboundAddEntityPacket spawnPacket = new ClientboundAddEntityPacket(
                    entity.entityId(), entity.uuid(), pos.x(), pos.y(), pos.z(), pos.yaw(), pos.pitch(),
                    PaperEntityTypes.toNms(entity.entityType()), 0, Vec3.ZERO, pos.yaw()
            );
            ClientboundSetEntityDataPacket metaPacket = entries.isEmpty()
                    ? null
                    : PaperMetaPackets.toPacket(entity.entityId(), entries, group.getKey());

            for (UUID viewer : group.getValue()) {
                sendPackets(viewer, spawnPacket, metaPacket);
            }
        }

        entity.meta().markSynced();
    }

    @Override
    public void updateMeta(LightweightExoEntity entity, Collection<UUID> viewers) {
        if (viewers.isEmpty()) return;

        List<MetaEntry<?>> dirty = entity.meta().dirtyEntries();
        if (dirty.isEmpty()) return;

        for (Map.Entry<Integer, List<UUID>> group : MetaViewerGrouping.groupByProtocolVersion(viewers).entrySet()) {
            ClientboundSetEntityDataPacket metaPacket = PaperMetaPackets.toPacket(entity.entityId(), dirty, group.getKey());
            if (metaPacket == null) continue;

            for (UUID viewer : group.getValue()) {
                Packets.INSTANCE.sendPackets(viewer, metaPacket);
            }
        }

        entity.meta().markSynced();
    }

    @Override
    public void despawn(LightweightExoEntity entity, Collection<UUID> viewers) {
        if (viewers.isEmpty()) return;

        ClientboundRemoveEntitiesPacket removePacket = new ClientboundRemoveEntitiesPacket(entity.entityId());
        for (UUID viewer : viewers) {
            Packets.INSTANCE.sendPackets(viewer, removePacket);
        }
    }

    private static void sendPackets(UUID viewer, ClientboundAddEntityPacket spawnPacket, @Nullable ClientboundSetEntityDataPacket metaPacket) {
        if (metaPacket != null) {
            Packets.INSTANCE.sendPackets(viewer, spawnPacket, metaPacket);
        } else {
            Packets.INSTANCE.sendPackets(viewer, spawnPacket);
        }
    }
}
