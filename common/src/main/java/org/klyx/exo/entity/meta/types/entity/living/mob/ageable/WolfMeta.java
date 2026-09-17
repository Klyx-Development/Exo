package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.ExoVariant;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.util.Key;

public final class WolfMeta extends TameableAnimalMeta {

    private static final MetaAccessor<Boolean> IS_BEGGING = new MetaAccessor<>(20, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Integer> COLLAR_COLOR = new MetaAccessor<>(21, MetaType.INT, 14);
    private static final MetaAccessor<Long> ANGER_END_TIME = new MetaAccessor<>(22, MetaType.LONG, -1L);
    private static final MetaAccessor<ExoVariant> VARIANT =
            new MetaAccessor<>(23, MetaType.WOLF_VARIANT, new ExoVariant(Key.of("pale")));
    private static final MetaAccessor<ExoVariant> SOUND_VARIANT =
            new MetaAccessor<>(24, MetaType.WOLF_SOUND_VARIANT, new ExoVariant(Key.of("classic")));

    public WolfMeta setBegging(boolean begging) {
        set(IS_BEGGING, begging);
        return this;
    }

    public boolean isBegging() {
        return get(IS_BEGGING);
    }

    public WolfMeta setCollarColor(int color) {
        set(COLLAR_COLOR, color);
        return this;
    }

    public int getCollarColor() {
        return get(COLLAR_COLOR);
    }

    public WolfMeta setAngerEndTime(long time) {
        set(ANGER_END_TIME, time);
        return this;
    }

    public long getAngerEndTime() {
        return get(ANGER_END_TIME);
    }

    public WolfMeta setVariant(ExoVariant variant) {
        set(VARIANT, variant);
        return this;
    }

    public ExoVariant getVariant() {
        return get(VARIANT);
    }

    public WolfMeta setSoundVariant(ExoVariant variant) {
        set(SOUND_VARIANT, variant);
        return this;
    }

    public ExoVariant getSoundVariant() {
        return get(SOUND_VARIANT);
    }
}
