package org.klyx.exo.entity.meta.types.living;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataSerializers;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.types.EntityMeta;

import java.util.List;
import java.util.Optional;

public abstract class LivingEntityMeta extends EntityMeta {

    private static final MetaAccessor<Byte> LIVING_ENTITY_FLAGS =
            new MetaAccessor<>(8, EntityDataSerializers.BYTE, (byte) 0);
    private static final MetaAccessor<Float> HEALTH =
            new MetaAccessor<>(9, EntityDataSerializers.FLOAT, 1.0f);
    private static final MetaAccessor<List<ParticleOptions>> EFFECT_PARTICLES =
            new MetaAccessor<>(10, EntityDataSerializers.PARTICLES, List.of());
    private static final MetaAccessor<Boolean> EFFECT_AMBIENCE =
            new MetaAccessor<>(11, EntityDataSerializers.BOOLEAN, false);
    private static final MetaAccessor<Integer> ARROW_COUNT =
            new MetaAccessor<>(12, EntityDataSerializers.INT, 0);
    private static final MetaAccessor<Integer> STINGER_COUNT =
            new MetaAccessor<>(13, EntityDataSerializers.INT, 0);
    private static final MetaAccessor<Optional<BlockPos>> SLEEPING_POS =
            new MetaAccessor<>(14, EntityDataSerializers.OPTIONAL_BLOCK_POS, Optional.empty());

    public LivingEntityMeta setUsingItem(boolean usingItem) {
        setFlag(LIVING_ENTITY_FLAGS, 0, usingItem);
        return this;
    }

    public boolean isUsingItem() {
        return getFlag(LIVING_ENTITY_FLAGS, 0);
    }

    public LivingEntityMeta setHealth(float health) {
        set(HEALTH, health);
        return this;
    }

    public float getHealth() {
        return get(HEALTH);
    }

    public LivingEntityMeta setEffectParticles(List<ParticleOptions> particles) {
        set(EFFECT_PARTICLES, particles);
        return this;
    }

    public List<ParticleOptions> getEffectParticles() {
        return get(EFFECT_PARTICLES);
    }

    public LivingEntityMeta setEffectAmbience(boolean ambient) {
        set(EFFECT_AMBIENCE, ambient);
        return this;
    }

    public boolean isEffectAmbient() {
        return get(EFFECT_AMBIENCE);
    }

    public LivingEntityMeta setArrowCount(int count) {
        set(ARROW_COUNT, count);
        return this;
    }

    public int getArrowCount() {
        return get(ARROW_COUNT);
    }

    public LivingEntityMeta setStingerCount(int count) {
        set(STINGER_COUNT, count);
        return this;
    }

    public int getStingerCount() {
        return get(STINGER_COUNT);
    }

    public LivingEntityMeta setSleepingPos(BlockPos pos) {
        set(SLEEPING_POS, Optional.ofNullable(pos));
        return this;
    }

    public Optional<BlockPos> getSleepingPos() {
        return get(SLEEPING_POS);
    }
}