package org.klyx.exotest;

import org.jspecify.annotations.NonNull;
import org.klyx.exo.entity.base.ExoHusk;
import org.klyx.exo.entity.data.EntityData;
import org.klyx.exo.paper.entity.components.types.HealthComponent;
import org.klyx.exo.paper.entity.components.types.HurtAnimationComponent;
import org.klyx.exo.paper.entity.components.types.KnockbackComponent;
import org.klyx.exo.paper.entity.components.types.tick.PhysicsComponent;

public class TestHealthEntity extends ExoHusk {

    @Override
    public EntityData.@NonNull Builder define() {
        return super.define()
                .components(new HealthComponent(), new HurtAnimationComponent(), new KnockbackComponent(), new PhysicsComponent());
    }
}
