package org.klyx.exo.common;

import org.klyx.exo.entity.ExoPos;
import org.klyx.exo.entity.LightweightExoEntity;

import java.util.Collection;
import java.util.UUID;

public interface LightweightEntityDispatcher {
    void spawn(LightweightExoEntity entity, ExoPos pos, Collection<UUID> viewers);

    void updateMeta(LightweightExoEntity entity, Collection<UUID> viewers);

    void despawn(LightweightExoEntity entity, Collection<UUID> viewers);
}
