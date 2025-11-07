package org.klyx.exo.entities.specific.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.base.BaseEntity;
import org.klyx.exo.entities.impl.AbstractEntity;

public class PacketEndCrystal extends BaseEntity {
    public PacketEndCrystal() {
        super(EntityType.END_CRYSTAL);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.EndCrystal.BEAM_TARGET);
        setMetadata(DataKeys.EndCrystal.SHOW_BOTTOM);
    }
}
