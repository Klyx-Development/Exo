package org.klyx.exo.entity.base;

import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.data.EntityData;
import org.klyx.exo.entity.data.ExoEntityType;
import org.klyx.exo.entity.meta.types.entity.OminousItemSpawnerMeta;
import org.klyx.exo.util.Key;

public class ExoOminousItemSpawner extends ExoEntity {
    @Override
    public EntityData.Builder define() {
        return EntityData.builder()
                .entityType(new ExoEntityType(Key.of("ominous_item_spawner")))
                .meta(OminousItemSpawnerMeta.class, _ -> {});
    }
}
