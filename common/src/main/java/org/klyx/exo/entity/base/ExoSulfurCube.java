package org.klyx.exo.entity.base;

import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.data.EntityData;
import org.klyx.exo.entity.data.ExoEntityType;
import org.klyx.exo.entity.meta.types.entity.living.mob.ageable.SulfurCubeMeta;
import org.klyx.exo.util.Key;

public class ExoSulfurCube extends ExoEntity {
    @Override
    public EntityData.Builder define() {
        return EntityData.builder()
                .entityType(new ExoEntityType(Key.of("sulfur_cube")))
                .meta(SulfurCubeMeta.class, _ -> {});
    }
}
