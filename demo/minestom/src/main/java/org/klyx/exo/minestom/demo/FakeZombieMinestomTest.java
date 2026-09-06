package org.klyx.exo.minestom.demo;

import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.data.EntityData;
import org.klyx.exo.entity.data.ExoEntityType;
import org.klyx.exo.entity.meta.types.EntityMeta;
import org.klyx.exo.util.Key;

public class FakeZombieMinestomTest extends ExoEntity {

    @Override
    public EntityData.Builder define() {
        return EntityData.builder()
                .entityType(new ExoEntityType(Key.of("zombie")))
                .meta(EntityMeta.class, meta -> meta.setGlowing(true));
    }

}
