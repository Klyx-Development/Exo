package org.klyx.exo.entities.specific.entity.displays;

import org.bukkit.entity.EntityType;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.data.metadata.BillboardConstraints;
import org.klyx.exo.entities.base.BaseEntity;
import org.klyx.exo.utils.MathUtil;

import javax.xml.crypto.Data;

public abstract class AbstractPacketDisplay extends BaseEntity {
    public AbstractPacketDisplay(@NotNull EntityType entityType) {
        super(entityType);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.Display.INTERPOLATION_DELAY);
        setMetadata(DataKeys.Display.TRANSFORMATION_INTERPOLATION_DURATION);
        setMetadata(DataKeys.Display.TELEPORT_DURATION);
        setMetadata(DataKeys.Display.TRANSLATION);
        setMetadata(DataKeys.Display.SCALE);
        setMetadata(DataKeys.Display.ROTATION_LEFT);
        setMetadata(DataKeys.Display.ROTATION_RIGHT);
        setMetadata(DataKeys.Display.BILLBOARD_CONSTRAINTS);
        setMetadata(DataKeys.Display.BRIGHTNESS_OVERRIDE);
        setMetadata(DataKeys.Display.VIEW_RANGE);
        setMetadata(DataKeys.Display.SHADOW_RADIUS);
        setMetadata(DataKeys.Display.SHADOW_STRENGTH);
        setMetadata(DataKeys.Display.WIDTH);
        setMetadata(DataKeys.Display.HEIGHT);
        setMetadata(DataKeys.Display.GLOW_COLOR_OVERRIDE);
    }

    public void setInterpolationDelay(int delay) {
        setMetadata(DataKeys.Display.INTERPOLATION_DELAY, delay);
    }

    public void setInterpolationDuration(int duration) {
        setMetadata(DataKeys.Display.TRANSFORMATION_INTERPOLATION_DURATION, duration);
    }

    public void setTeleportDuration(int duration) {
        setMetadata(DataKeys.Display.TELEPORT_DURATION, duration);
    }

    public void setTranslation(Vector vector) {
        setMetadata(DataKeys.Display.TRANSLATION, new Vector3f((float) vector.getX(), (float) vector.getY(), (float) vector.getZ()));
    }

    public void setScale(Vector vector) {
        setMetadata(DataKeys.Display.SCALE, new Vector3f((float) vector.getX(), (float) vector.getY(), (float) vector.getZ()));
    }

    public void setRotationLeft(Vector vector) {
        setMetadata(DataKeys.Display.ROTATION_LEFT, MathUtil.eulerToQuaternion(vector.getX(), vector.getY(), vector.getZ()));
    }

    public void setRotationRight(Vector vector) {
        setMetadata(DataKeys.Display.ROTATION_RIGHT, MathUtil.eulerToQuaternion(vector.getX(), vector.getY(), vector.getZ()));
    }

    public void setBillboardConstraints(BillboardConstraints billboardConstraints) {
        setMetadata(DataKeys.Display.BILLBOARD_CONSTRAINTS, (byte) billboardConstraints.getValue());
    }

    public void setTransform(@NotNull Matrix4f matrix) {
        Vector3f translation = new Vector3f();
        Vector3f scale = new Vector3f();
        Quaternionf rotation = new Quaternionf();

        matrix.getTranslation(translation);
        matrix.getScale(scale);
        matrix.getUnnormalizedRotation(rotation);

        setMetadata(DataKeys.Display.TRANSLATION, translation);
        setMetadata(DataKeys.Display.SCALE, scale);
        setMetadata(DataKeys.Display.ROTATION_LEFT, rotation);
        setMetadata(DataKeys.Display.ROTATION_RIGHT, new Quaternionf());
    }

    public void setTransform(Transformation transformation) {
        setMetadata(DataKeys.Display.TRANSLATION, transformation.getTranslation());
        setMetadata(DataKeys.Display.SCALE, transformation.getScale());
        setMetadata(DataKeys.Display.ROTATION_LEFT, transformation.getLeftRotation());
        setMetadata(DataKeys.Display.ROTATION_RIGHT, transformation.getRightRotation());
    }
}
