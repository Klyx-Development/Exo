package org.klyx.exo.entities.specific.livingentity;

import org.bukkit.entity.EntityType;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.base.BaseLivingEntity;

public class PacketArmorStand extends BaseLivingEntity {
    public PacketArmorStand() {
        super(EntityType.ARMOR_STAND);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.ArmorStand.ARMOR_STAND_OPTIONS);
        setMetadata(DataKeys.ArmorStand.HEAD_ROTATION);
        setMetadata(DataKeys.ArmorStand.BODY_ROTATION);
        setMetadata(DataKeys.ArmorStand.LEFT_ARM_ROTATION);
        setMetadata(DataKeys.ArmorStand.RIGHT_ARM_ROTATION);
        setMetadata(DataKeys.ArmorStand.LEFT_LEG_ROTATION);
        setMetadata(DataKeys.ArmorStand.RIGHT_LEG_ROTATION);
    }
}
