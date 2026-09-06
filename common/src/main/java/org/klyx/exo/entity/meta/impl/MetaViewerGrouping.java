package org.klyx.exo.entity.meta.impl;

import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.player.ExoPlayer;
import org.klyx.exo.common.ViewerVisibilityQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class MetaViewerGrouping {

    /** Falls back to {@link MetaAccessor#LATEST} if there's no player, or if the version is unknown/-1 */
    public static int resolveVersion(@Nullable ExoPlayer player) {
        if (player == null) return MetaAccessor.LATEST;
        int version = player.protocolVersion();
        return version > 0 ? version : MetaAccessor.LATEST;
    }

    public static int resolveVersion(UUID viewer) {
        return resolveVersion(Exo.platform().viewerVisibilityQuery().resolvePlayer(viewer));
    }

    public static Map<Integer, List<UUID>> groupByProtocolVersion(Collection<UUID> viewers) {
        ViewerVisibilityQuery query = Exo.platform().viewerVisibilityQuery();
        Map<Integer, List<UUID>> grouped = new HashMap<>();
        for (UUID viewer : viewers) {
            int version = resolveVersion(query.resolvePlayer(viewer));
            grouped.computeIfAbsent(version, v -> new ArrayList<>()).add(viewer);
        }
        return grouped;
    }
}
