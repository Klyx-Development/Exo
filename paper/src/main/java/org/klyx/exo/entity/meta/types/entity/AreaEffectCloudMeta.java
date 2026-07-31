package org.klyx.exo.entity.meta.types.entity;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataSerializers;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.types.EntityMeta;

public class AreaEffectCloudMeta extends EntityMeta {

    private static final MetaAccessor<Float> RADIUS = new MetaAccessor<>(8, EntityDataSerializers.FLOAT, 3.0F);
    private static final MetaAccessor<Boolean> WAITING = new MetaAccessor<>(9, EntityDataSerializers.BOOLEAN, false);
    private static final MetaAccessor<ParticleOptions> PARTICLE = new MetaAccessor<>(10, EntityDataSerializers.PARTICLE, DustParticleOptions.REDSTONE);

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

    public AreaEffectCloudMeta setParticle(ParticleOptions particle) {
        set(PARTICLE, particle);
        return this;
    }

    public ParticleOptions getParticle() {
        return get(PARTICLE);
    }

}
