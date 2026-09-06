package org.klyx.exo.entity.meta.types.entity.arrow;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class TridentMeta extends AbstractArrowMeta {

    private static final MetaAccessor<Byte> LOYALTY_LEVEL = new MetaAccessor<>(11, MetaType.BYTE, (byte) 0);
    private static final MetaAccessor<Boolean> ENCHANTMENT_GLINT = new MetaAccessor<>(12, MetaType.BOOLEAN, false);

    public TridentMeta setLoyaltyLevel(byte loyaltyLevel) {
        set(LOYALTY_LEVEL, loyaltyLevel);
        return this;
    }

    public byte getLoyaltyLevel() {
        return get(LOYALTY_LEVEL);
    }

    public TridentMeta setEnchantmentGlint(boolean enchantmentGlint) {
        set(ENCHANTMENT_GLINT, enchantmentGlint);
        return this;
    }

    public boolean isEnchantmentGlint() {
        return get(ENCHANTMENT_GLINT);
    }

}
