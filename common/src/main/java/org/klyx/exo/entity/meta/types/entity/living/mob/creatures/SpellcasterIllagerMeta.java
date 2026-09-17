package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class SpellcasterIllagerMeta extends RaiderMeta {

    private static final MetaAccessor<Byte> SPELL_CASTING = new MetaAccessor<>(17, MetaType.BYTE, (byte) 0);

    public SpellcasterIllagerMeta setSpellCasting(byte spell) {
        set(SPELL_CASTING, spell);
        return this;
    }

    public byte getSpellCasting() {
        return get(SPELL_CASTING);
    }
}
