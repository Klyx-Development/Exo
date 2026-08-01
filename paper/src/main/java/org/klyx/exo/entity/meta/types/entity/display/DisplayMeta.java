package org.klyx.exo.entity.meta.types.entity.display;

import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.Display;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Range;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.types.EntityMeta;
import org.klyx.exo.util.QuaternionHelper;

public abstract class DisplayMeta extends EntityMeta {

    private static final MetaAccessor<Integer> TRANSFORMATION_INTERPOLATION_START_DELTA_TICKS =
            new MetaAccessor<>(8, EntityDataSerializers.INT, 0);
    private static final MetaAccessor<Integer> TRANSFORMATION_INTERPOLATION_DURATION =
            new MetaAccessor<>(9, EntityDataSerializers.INT, 0);
    private static final MetaAccessor<Integer> POS_ROT_INTERPOLATION_DURATION =
            new MetaAccessor<>(10, EntityDataSerializers.INT, 0);
    private static final MetaAccessor<Vector3fc> TRANSLATION =
            new MetaAccessor<>(11, EntityDataSerializers.VECTOR3, new Vector3f());
    private static final MetaAccessor<Vector3fc> SCALE =
            new MetaAccessor<>(12, EntityDataSerializers.VECTOR3, new Vector3f(1, 1, 1));
    private static final MetaAccessor<Quaternionfc> LEFT_ROTATION =
            new MetaAccessor<>(13, EntityDataSerializers.QUATERNION, new Quaternionf());
    private static final MetaAccessor<Quaternionfc> RIGHT_ROTATION =
            new MetaAccessor<>(14, EntityDataSerializers.QUATERNION, new Quaternionf());
    private static final MetaAccessor<Byte> BILLBOARD_CONSTRAINTS =
            new MetaAccessor<>(15, EntityDataSerializers.BYTE, (byte) 0);
    private static final MetaAccessor<Integer> BRIGHTNESS_OVERRIDE =
            new MetaAccessor<>(16, EntityDataSerializers.INT, -1);
    private static final MetaAccessor<Float> VIEW_RANGE =
            new MetaAccessor<>(17, EntityDataSerializers.FLOAT, 1.0f);
    private static final MetaAccessor<Float> SHADOW_RADIUS =
            new MetaAccessor<>(18, EntityDataSerializers.FLOAT, 0.0f);
    private static final MetaAccessor<Float> SHADOW_STRENGTH =
            new MetaAccessor<>(19, EntityDataSerializers.FLOAT, 1.0f);
    private static final MetaAccessor<Float> WIDTH =
            new MetaAccessor<>(20, EntityDataSerializers.FLOAT, 0.0f);
    private static final MetaAccessor<Float> HEIGHT =
            new MetaAccessor<>(21, EntityDataSerializers.FLOAT, 0.0f);
    private static final MetaAccessor<Integer> GLOW_COLOR_OVERRIDE =
            new MetaAccessor<>(22, EntityDataSerializers.INT, -1);

    public DisplayMeta setInterpolationDelay(int ticks) {
        set(TRANSFORMATION_INTERPOLATION_START_DELTA_TICKS, ticks);
        return this;
    }

    public int getInterpolationDelay() {
        return get(TRANSFORMATION_INTERPOLATION_START_DELTA_TICKS);
    }

    public DisplayMeta setInterpolationDuration(int ticks) {
        set(TRANSFORMATION_INTERPOLATION_DURATION, ticks);
        return this;
    }

    public int getInterpolationDuration() {
        return get(TRANSFORMATION_INTERPOLATION_DURATION);
    }

    public DisplayMeta setTeleportDuration(int ticks) {
        set(POS_ROT_INTERPOLATION_DURATION, ticks);
        return this;
    }

    public int getTeleportDuration() {
        return get(POS_ROT_INTERPOLATION_DURATION);
    }

    public DisplayMeta setTranslation(Vector3fc translation) {
        set(TRANSLATION, translation);
        return this;
    }

    public DisplayMeta setTranslation(Vector translation) {
        set(TRANSLATION, translation.toVector3f());
        return this;
    }

    public Vector3fc getTranslation() {
        return get(TRANSLATION);
    }

    public Vector getTranslationBukkit() {
        Vector3fc translation = getTranslation();
        return new Vector(translation.x(), translation.y(), translation.z());
    }

    public DisplayMeta setScale(Vector3fc scale) {
        set(SCALE, scale);
        return this;
    }

    public DisplayMeta setScale(Vector scale) {
        set(SCALE, scale.toVector3f());
        return this;
    }

    public Vector3fc getScale() {
        return get(SCALE);
    }

    public Vector getScaleBukkit() {
        Vector3fc scale = getScale();
        return new Vector(scale.x(), scale.y(), scale.z());
    }

    public DisplayMeta setLeftRotation(Quaternionfc rotation) {
        set(LEFT_ROTATION, rotation);
        return this;
    }

    public DisplayMeta setLeftRotation(Vector rotation) {
        set(LEFT_ROTATION, QuaternionHelper.toQuaternion(rotation));
        return this;
    }

    public Quaternionfc getLeftRotation() {
        return get(LEFT_ROTATION);
    }

    public DisplayMeta setRightRotation(Quaternionfc rotation) {
        set(RIGHT_ROTATION, rotation);
        return this;
    }

    public DisplayMeta setRightRotation(Vector rotation) {
        set(RIGHT_ROTATION, QuaternionHelper.toQuaternion(rotation));
        return this;
    }

    public Quaternionfc getRightRotation() {
        return get(RIGHT_ROTATION);
    }

    public DisplayMeta setBillboardConstraints(Display.BillboardConstraints constraints) {
        set(BILLBOARD_CONSTRAINTS, (byte) constraints.ordinal());
        return this;
    }

    public byte getBillboardConstraints() {
        return get(BILLBOARD_CONSTRAINTS);
    }

    public DisplayMeta setBrightnessOverride(@Range(from = 0, to = 15) int blockLight, @Range(from = 0, to = 15) int skyLight) {
        set(BRIGHTNESS_OVERRIDE, (blockLight << 4 | skyLight << 20));
        return this;
    }

    public DisplayMeta setBrightnessOverride(@Range(from = 0, to = 15) int brightness) {
        return setBrightnessOverride(brightness, brightness);
    }

    public int getBrightnessOverride() {
        return get(BRIGHTNESS_OVERRIDE);
    }

    public boolean hasBrightnessOverride() {
        return getBrightnessOverride() != -1;
    }

    public DisplayMeta setViewRange(float range) {
        set(VIEW_RANGE, range);
        return this;
    }

    public float getViewRange() {
        return get(VIEW_RANGE);
    }

    public DisplayMeta setShadowRadius(float radius) {
        set(SHADOW_RADIUS, radius);
        return this;
    }

    public float getShadowRadius() {
        return get(SHADOW_RADIUS);
    }

    public DisplayMeta setShadowStrength(float strength) {
        set(SHADOW_STRENGTH, strength);
        return this;
    }

    public float getShadowStrength() {
        return get(SHADOW_STRENGTH);
    }

    public DisplayMeta setWidth(float width) {
        set(WIDTH, width);
        return this;
    }

    public float getWidth() {
        return get(WIDTH);
    }

    public DisplayMeta setHeight(float height) {
        set(HEIGHT, height);
        return this;
    }

    public float getHeight() {
        return get(HEIGHT);
    }

    public DisplayMeta setGlowColorOverride(int color) {
        set(GLOW_COLOR_OVERRIDE, color);
        return this;
    }

    public int getGlowColorOverride() {
        return get(GLOW_COLOR_OVERRIDE);
    }

    public boolean hasGlowColorOverride() {
        return getGlowColorOverride() != -1;
    }
}