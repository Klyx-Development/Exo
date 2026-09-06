package org.klyx.exo.entity.meta.types.entity.living;

import org.klyx.exo.entity.meta.ExoRotations;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public class ArmorStandMeta extends LivingEntityMeta {

    private static final MetaAccessor<Byte> ARMOR_STAND_FLAGS = new MetaAccessor<>(15, MetaType.BYTE, (byte) 0);
    private static final MetaAccessor<ExoRotations> HEAD_ROTATION = new MetaAccessor<>(16, MetaType.ROTATIONS, new ExoRotations(0, 0, 0));
    private static final MetaAccessor<ExoRotations> BODY_ROTATION = new MetaAccessor<>(17, MetaType.ROTATIONS, new ExoRotations(0, 0, 0));
    private static final MetaAccessor<ExoRotations> LEFT_ARM_ROTATION = new MetaAccessor<>(18, MetaType.ROTATIONS, new ExoRotations(-10, 0, -10));
    private static final MetaAccessor<ExoRotations> RIGHT_ARM_ROTATION = new MetaAccessor<>(19, MetaType.ROTATIONS, new ExoRotations(-15, 0, 10));
    private static final MetaAccessor<ExoRotations> LEFT_LEG_ROTATION = new MetaAccessor<>(20, MetaType.ROTATIONS, new ExoRotations(-1, 0, -1));
    private static final MetaAccessor<ExoRotations> RIGHT_LEG_ROTATION = new MetaAccessor<>(21, MetaType.ROTATIONS, new ExoRotations(1, 0, 1));

    public ArmorStandMeta setSmall(boolean small) {
        setFlag(ARMOR_STAND_FLAGS, 0, small);
        return this;
    }

    public boolean isSmall() {
        return getFlag(ARMOR_STAND_FLAGS, 0);
    }

    public ArmorStandMeta setHasArms(boolean hasArms) {
        setFlag(ARMOR_STAND_FLAGS, 2, hasArms);
        return this;
    }

    public boolean hasArms() {
        return getFlag(ARMOR_STAND_FLAGS, 2);
    }

    public ArmorStandMeta setBasePlate(boolean basePlate) {
        setFlag(ARMOR_STAND_FLAGS, 3, !basePlate);
        return this;
    }

    public boolean hasBasePlate() {
        return !getFlag(ARMOR_STAND_FLAGS, 3);
    }

    public ArmorStandMeta setMarker(boolean marker) {
        setFlag(ARMOR_STAND_FLAGS, 4, marker);
        return this;
    }

    public boolean isMarker() {
        return getFlag(ARMOR_STAND_FLAGS, 4);
    }

    public ArmorStandMeta setHeadRotation(ExoRotations rotation) {
        set(HEAD_ROTATION, rotation);
        return this;
    }

    public ExoRotations getHeadRotation() {
        return get(HEAD_ROTATION);
    }

    public ArmorStandMeta setBodyRotation(ExoRotations rotation) {
        set(BODY_ROTATION, rotation);
        return this;
    }

    public ExoRotations getBodyRotation() {
        return get(BODY_ROTATION);
    }

    public ArmorStandMeta setLeftArmRotation(ExoRotations rotation) {
        set(LEFT_ARM_ROTATION, rotation);
        return this;
    }

    public ExoRotations getLeftArmRotation() {
        return get(LEFT_ARM_ROTATION);
    }

    public ArmorStandMeta setRightArmRotation(ExoRotations rotation) {
        set(RIGHT_ARM_ROTATION, rotation);
        return this;
    }

    public ExoRotations getRightArmRotation() {
        return get(RIGHT_ARM_ROTATION);
    }

    public ArmorStandMeta setLeftLegRotation(ExoRotations rotation) {
        set(LEFT_LEG_ROTATION, rotation);
        return this;
    }

    public ExoRotations getLeftLegRotation() {
        return get(LEFT_LEG_ROTATION);
    }

    public ArmorStandMeta setRightLegRotation(ExoRotations rotation) {
        set(RIGHT_LEG_ROTATION, rotation);
        return this;
    }

    public ExoRotations getRightLegRotation() {
        return get(RIGHT_LEG_ROTATION);
    }

}
