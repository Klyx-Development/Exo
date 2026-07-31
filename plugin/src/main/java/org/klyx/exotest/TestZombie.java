package org.klyx.exotest;

import org.bukkit.entity.EntityType;
import org.jspecify.annotations.NonNull;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.components.types.PassengerComponent;
import org.klyx.exo.entity.components.types.TickComponent;
import org.klyx.exo.entity.data.EntityData;
import org.klyx.exo.entity.meta.types.entity.living.mob.creatures.ZombieMeta;

public class TestZombie extends ExoEntity {

    @Override
    public EntityData.@NonNull Builder define() {
        return EntityData.builder()
                .entityType(EntityType.ZOMBIE)
                .components(new TickComponent(), new PassengerComponent())
                .meta(ZombieMeta.class, meta -> {
                    meta.setGlowing(true);
                    meta.setBaby(true);
                });
    }

}
