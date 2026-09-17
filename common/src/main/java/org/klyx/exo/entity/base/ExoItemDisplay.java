package org.klyx.exo.entity.base;

import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.data.EntityData;
import org.klyx.exo.entity.data.ExoEntityType;
import org.klyx.exo.entity.meta.types.entity.display.ItemDisplayMeta;
import org.klyx.exo.util.Key;

public class ExoItemDisplay extends ExoEntity {
    @Override
    public EntityData.Builder define() {
        return EntityData.builder()
                .entityType(new ExoEntityType(Key.of("item_display")))
                .meta(ItemDisplayMeta.class, _ -> {});
    }
}
