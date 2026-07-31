package org.klyx.exo.entity.meta.types.entity.living.avatar;

import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.HumanoidArm;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.types.entity.living.LivingEntityMeta;

public class AvatarMeta extends LivingEntityMeta {

    private static final MetaAccessor<HumanoidArm> HUMANOID_ARM = new MetaAccessor<>(15, EntityDataSerializers.HUMANOID_ARM, HumanoidArm.RIGHT);
    private static final MetaAccessor<Byte> SKIN_CUSTOMIZATION = new MetaAccessor<>(16, EntityDataSerializers.BYTE, (byte) 0);

    public AvatarMeta setMainHand(HumanoidArm arm) {
        set(HUMANOID_ARM, arm);
        return this;
    }

    public HumanoidArm getMainHand() {
        return get(HUMANOID_ARM);
    }

    public AvatarMeta setCapeEnabled(boolean enabled) {
        setFlag(SKIN_CUSTOMIZATION, 0, enabled);
        return this;
    }

    public boolean isCapeEnabled() {
        return getFlag(SKIN_CUSTOMIZATION, 0);
    }

    public AvatarMeta setJacketEnabled(boolean enabled) {
        setFlag(SKIN_CUSTOMIZATION, 1, enabled);
        return this;
    }

    public boolean isJacketEnabled() {
        return getFlag(SKIN_CUSTOMIZATION, 1);
    }

    public AvatarMeta setLeftSleeveEnabled(boolean enabled) {
        setFlag(SKIN_CUSTOMIZATION, 2, enabled);
        return this;
    }

    public boolean isLeftSleeveEnabled() {
        return getFlag(SKIN_CUSTOMIZATION, 2);
    }

    public AvatarMeta setRightSleeveEnabled(boolean enabled) {
        setFlag(SKIN_CUSTOMIZATION, 3, enabled);
        return this;
    }

    public boolean isRightSleeveEnabled() {
        return getFlag(SKIN_CUSTOMIZATION, 3);
    }

    public AvatarMeta setLeftPantsLegEnabled(boolean enabled) {
        setFlag(SKIN_CUSTOMIZATION, 4, enabled);
        return this;
    }

    public boolean isLeftPantsLegEnabled() {
        return getFlag(SKIN_CUSTOMIZATION, 4);
    }

    public AvatarMeta setRightPantsLegEnabled(boolean enabled) {
        setFlag(SKIN_CUSTOMIZATION, 5, enabled);
        return this;
    }

    public boolean isRightPantsLegEnabled() {
        return getFlag(SKIN_CUSTOMIZATION, 5);
    }

    public AvatarMeta setHatEnabled(boolean enabled) {
        setFlag(SKIN_CUSTOMIZATION, 6, enabled);
        return this;
    }

    public boolean isHatEnabled() {
        return getFlag(SKIN_CUSTOMIZATION, 6);
    }

}