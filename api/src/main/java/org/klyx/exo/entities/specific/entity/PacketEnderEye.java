package org.klyx.exo.entities.specific.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.base.BaseEntity;
import org.klyx.exo.entities.impl.AbstractEntity;

public class PacketEnderEye extends BaseEntity {
    public PacketEnderEye() {
        super(EntityType.EYE_OF_ENDER);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.EnderEye.ITEM_STACK);
    }
}
