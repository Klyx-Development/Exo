package org.klyx.exo.entity.meta.types.entity;

import org.klyx.exo.entity.meta.ExoParticle;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.entity.meta.types.EntityMeta;
import org.klyx.exo.util.Key;

public class AreaEffectCloudMeta extends EntityMeta {

    private static final MetaAccessor<Float> RADIUS = new MetaAccessor<>(8, MetaType.FLOAT, 3.0F);
    private static final MetaAccessor<Boolean> WAITING = new MetaAccessor<>(9, MetaType.BOOLEAN, false);
    private static final MetaAccessor<ExoParticle> PARTICLE = new MetaAccessor<>(10, MetaType.PARTICLE, new ExoParticle(Key.of("dust"), null));

    public AreaEffectCloudMeta setRadius(float radius) {
        set(RADIUS, radius);
        return this;
    }

    public float getRadius() {
        return get(RADIUS);
    }

    public AreaEffectCloudMeta setWaiting(boolean waiting) {
        set(WAITING, waiting);
        return this;
    }

    public boolean getWaiting() {
        return get(WAITING);
    }

    public AreaEffectCloudMeta setParticle(ExoParticle particle) {
        set(PARTICLE, particle);
        return this;
    }

    public ExoParticle getParticle() {
        return get(PARTICLE);
    }

}
