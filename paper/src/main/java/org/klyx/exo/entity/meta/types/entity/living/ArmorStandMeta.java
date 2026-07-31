package org.klyx.exo.entity.meta.types.entity.living;

import net.minecraft.core.Rotations;
import net.minecraft.network.syncher.EntityDataSerializers;
import org.klyx.exo.entity.meta.impl.MetaAccessor;

public class ArmorStandMeta extends LivingEntityMeta {

    private static final MetaAccessor<Byte> ARMOR_STAND_FLAGS = new MetaAccessor<>(15, EntityDataSerializers.BYTE, (byte) 0);
    private static final MetaAccessor<Rotations> HEAD_ROTATION = new MetaAccessor<>(16, EntityDataSerializers.ROTATIONS, new Rotations(0, 0, 0));
    private static final MetaAccessor<Rotations> BODY_ROTATION = new MetaAccessor<>(17, EntityDataSerializers.ROTATIONS, new Rotations(0, 0, 0));
    private static final MetaAccessor<Rotations> LEFT_ARM_ROTATION = new MetaAccessor<>(18, EntityDataSerializers.ROTATIONS, new Rotations(-10, 0, -10));
    private static final MetaAccessor<Rotations> RIGHT_ARM_ROTATION = new MetaAccessor<>(19, EntityDataSerializers.ROTATIONS, new Rotations(-15, 0, 10));
    private static final MetaAccessor<Rotations> LEFT_LEG_ROTATION = new MetaAccessor<>(20, EntityDataSerializers.ROTATIONS, new Rotations(-1, 0, -1));
    private static final MetaAccessor<Rotations> RIGHT_LEG_ROTATION = new MetaAccessor<>(21, EntityDataSerializers.ROTATIONS, new Rotations(1, 0, 1));

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

    public ArmorStandMeta setHeadRotation(Rotations rotation) {
        set(HEAD_ROTATION, rotation);
        return this;
    }

    public Rotations getHeadRotation() {
        return get(HEAD_ROTATION);
    }

    public ArmorStandMeta setBodyRotation(Rotations rotation) {
        set(BODY_ROTATION, rotation);
        return this;
    }

    public Rotations getBodyRotation() {
        return get(BODY_ROTATION);
    }

    public ArmorStandMeta setLeftArmRotation(Rotations rotation) {
        set(LEFT_ARM_ROTATION, rotation);
        return this;
    }

    public Rotations getLeftArmRotation() {
        return get(LEFT_ARM_ROTATION);
    }

    public ArmorStandMeta setRightArmRotation(Rotations rotation) {
        set(RIGHT_ARM_ROTATION, rotation);
        return this;
    }

    public Rotations getRightArmRotation() {
        return get(RIGHT_ARM_ROTATION);
    }

    public ArmorStandMeta setLeftLegRotation(Rotations rotation) {
        set(LEFT_LEG_ROTATION, rotation);
        return this;
    }

    public Rotations getLeftLegRotation() {
        return get(LEFT_LEG_ROTATION);
    }

    public ArmorStandMeta setRightLegRotation(Rotations rotation) {
        set(RIGHT_LEG_ROTATION, rotation);
        return this;
    }

    public Rotations getRightLegRotation() {
        return get(RIGHT_LEG_ROTATION);
    }
    
}
