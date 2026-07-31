package org.klyx.exotest;

import org.bukkit.entity.EntityType;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.components.types.tick.LookAtComponent;
import org.klyx.exo.entity.data.EntityData;
import org.klyx.exo.entity.meta.types.entity.living.avatar.MannequinMeta;

public class TestMannequin extends ExoEntity {

    @Override
    public EntityData.Builder define() {
        return EntityData.builder()
                .entityType(EntityType.MANNEQUIN)
                .component(new LookAtComponent())
                .meta(MannequinMeta.class, meta -> {
                    meta.setImmovable(true);
                });
    }

}
