package org.klyx.exo.minestom.dispatch;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.server.play.DestroyEntitiesPacket;
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;
import net.minestom.server.network.packet.server.play.SpawnEntityPacket;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.common.LightweightEntityDispatcher;
import org.klyx.exo.entity.ExoPos;
import org.klyx.exo.entity.LightweightExoEntity;
import org.klyx.exo.entity.meta.impl.MetaEntry;
import org.klyx.exo.entity.meta.impl.MetaViewerGrouping;
import org.klyx.exo.minestom.entity.MinestomEntityTypes;
import org.klyx.exo.minestom.meta.MinestomMetaPackets;
import org.klyx.exo.minestom.util.MinestomLocationHelper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class MinestomLightweightEntityDispatcher implements LightweightEntityDispatcher {

    @Override
    public void spawn(LightweightExoEntity entity, ExoPos pos, Collection<UUID> viewers) {
        if (viewers.isEmpty()) return;

        SpawnEntityPacket spawnPacket = new SpawnEntityPacket(
                entity.entityId(), entity.uuid(), MinestomEntityTypes.toMinestom(entity.entityType()),
                MinestomLocationHelper.toPos(pos), pos.yaw(), 0, Vec.ZERO
        );

        List<MetaEntry<?>> entries = entity.meta().toEntries();
        for (Map.Entry<Integer, List<UUID>> group : MetaViewerGrouping.groupByProtocolVersion(viewers).entrySet()) {
            EntityMetaDataPacket metaPacket = entries.isEmpty()
                    ? null
                    : MinestomMetaPackets.toPacket(entity.entityId(), entries, group.getKey());

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
            EntityMetaDataPacket metaPacket = MinestomMetaPackets.toPacket(entity.entityId(), dirty, group.getKey());
            if (metaPacket == null) continue;

            for (UUID viewer : group.getValue()) {
                Player player = resolvePlayer(viewer);
                if (player != null) player.sendPacket(metaPacket);
            }
        }

        entity.meta().markSynced();
    }

    @Override
    public void despawn(LightweightExoEntity entity, Collection<UUID> viewers) {
        if (viewers.isEmpty()) return;

        DestroyEntitiesPacket removePacket = new DestroyEntitiesPacket(entity.entityId());
        for (UUID viewer : viewers) {
            Player player = resolvePlayer(viewer);
            if (player != null) player.sendPacket(removePacket);
        }
    }

    private static void sendPackets(UUID viewer, SpawnEntityPacket spawnPacket, @Nullable EntityMetaDataPacket metaPacket) {
        Player player = resolvePlayer(viewer);
        if (player == null) return;

        player.sendPacket(spawnPacket);
        if (metaPacket != null) player.sendPacket(metaPacket);
    }

    private static @Nullable Player resolvePlayer(UUID uuid) {
        return MinecraftServer.getConnectionManager().getOnlinePlayerByUuid(uuid);
    }
}
