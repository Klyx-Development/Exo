package org.klyx.exotest;

import org.bukkit.entity.EntityType;
import org.jspecify.annotations.NonNull;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.paper.entity.components.types.PassengerComponent;
import org.klyx.exo.paper.entity.components.types.tick.TickComponent;
import org.klyx.exo.entity.data.EntityData;
import org.klyx.exo.entity.meta.types.entity.living.mob.creatures.ZombieMeta;
import org.klyx.exo.paper.entity.PaperEntityTypes;

public class TestZombie extends ExoEntity {

    @Override
    public EntityData.@NonNull Builder define() {
        return EntityData.builder()
                .entityType(PaperEntityTypes.toExo(EntityType.ZOMBIE))
                .components(new TickComponent(), new PassengerComponent())
                .meta(ZombieMeta.class, meta -> {
                    meta.setGlowing(true);
                    meta.setBaby(true);
                });
    }

}
