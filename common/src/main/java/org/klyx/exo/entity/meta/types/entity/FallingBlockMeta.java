package org.klyx.exo.entity.meta.types.entity;

import org.klyx.exo.entity.meta.ExoBlockPos;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.entity.meta.types.EntityMeta;

public class FallingBlockMeta extends EntityMeta {

    private static final MetaAccessor<ExoBlockPos> SPAWN_POSITION = new MetaAccessor<>(8, MetaType.BLOCK_POS, ExoBlockPos.ZERO);

    public FallingBlockMeta setSpawnPosition(ExoBlockPos pos) {
        set(SPAWN_POSITION, pos);
        return this;
    }

    public ExoBlockPos getSpawnPosition() {
        return get(SPAWN_POSITION);
    }

}
