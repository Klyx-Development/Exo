package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.ExoVariant;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.util.Key;

public final class CatMeta extends TameableAnimalMeta {

    private static final MetaAccessor<ExoVariant> VARIANT =
            new MetaAccessor<>(20, MetaType.CAT_VARIANT, new ExoVariant(Key.of("black")));
    private static final MetaAccessor<Boolean> IS_LYING = new MetaAccessor<>(21, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Boolean> RELAX_STATE_ONE = new MetaAccessor<>(22, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Integer> COLLAR_COLOR = new MetaAccessor<>(23, MetaType.INT, 14);
    private static final MetaAccessor<ExoVariant> SOUND_VARIANT =
            new MetaAccessor<>(24, MetaType.CAT_SOUND_VARIANT, new ExoVariant(Key.of("classic")));

    public CatMeta setVariant(ExoVariant variant) {
        set(VARIANT, variant);
        return this;
    }

    public ExoVariant getVariant() {
        return get(VARIANT);
    }

    public CatMeta setLying(boolean lying) {
        set(IS_LYING, lying);
        return this;
    }

    public boolean isLying() {
        return get(IS_LYING);
    }

    public CatMeta setRelaxStateOne(boolean relaxed) {
        set(RELAX_STATE_ONE, relaxed);
        return this;
    }

    public boolean isRelaxStateOne() {
        return get(RELAX_STATE_ONE);
    }

    public CatMeta setCollarColor(int color) {
        set(COLLAR_COLOR, color);
        return this;
    }

    public int getCollarColor() {
        return get(COLLAR_COLOR);
    }

    public CatMeta setSoundVariant(ExoVariant variant) {
        set(SOUND_VARIANT, variant);
        return this;
    }

    public ExoVariant getSoundVariant() {
        return get(SOUND_VARIANT);
    }
}
