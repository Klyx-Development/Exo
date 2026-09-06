package org.klyx.exo.entity.meta.types;

import net.kyori.adventure.text.Component;
import org.klyx.exo.entity.meta.Pose;
import org.klyx.exo.entity.meta.impl.AbstractEntityMeta;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

import java.util.Optional;

public class EntityMeta extends AbstractEntityMeta {

    private static final MetaAccessor<Byte> SHARED_FLAGS =
            new MetaAccessor<>(0, MetaType.BYTE, (byte) 0);
    private static final MetaAccessor<Integer> AIR_SUPPLY =
            new MetaAccessor<>(1, MetaType.INT, 300);
    private static final MetaAccessor<Optional<Component>> CUSTOM_NAME =
            new MetaAccessor<>(2, MetaType.OPTIONAL_COMPONENT, Optional.empty());
    private static final MetaAccessor<Boolean> CUSTOM_NAME_VISIBLE =
            new MetaAccessor<>(3, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Boolean> SILENT =
            new MetaAccessor<>(4, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Boolean> NO_GRAVITY =
            new MetaAccessor<>(5, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Pose> POSE =
            new MetaAccessor<>(6, MetaType.POSE, Pose.STANDING);
    private static final MetaAccessor<Integer> TICKS_FROZEN =
            new MetaAccessor<>(7, MetaType.INT, 0);

    public EntityMeta setOnFire(boolean onFire) {
        setFlag(SHARED_FLAGS, 0, onFire);
        return this;
    }

    public boolean isOnFire() {
        return getFlag(SHARED_FLAGS, 0);
    }

    public EntityMeta setSneaking(boolean sneaking) {
        setFlag(SHARED_FLAGS, 1, sneaking);
        return this;
    }

    public boolean isSneaking() {
        return getFlag(SHARED_FLAGS, 1);
    }

    public EntityMeta setSprinting(boolean sprinting) {
        setFlag(SHARED_FLAGS, 3, sprinting);
        return this;
    }

    public boolean isSprinting() {
        return getFlag(SHARED_FLAGS, 3);
    }

    public EntityMeta setInvisible(boolean invisible) {
        setFlag(SHARED_FLAGS, 5, invisible);
        return this;
    }

    public boolean isInvisible() {
        return getFlag(SHARED_FLAGS, 5);
    }

    public EntityMeta setGlowing(boolean glowing) {
        setFlag(SHARED_FLAGS, 6, glowing);
        return this;
    }

    public boolean isGlowing() {
        return getFlag(SHARED_FLAGS, 6);
    }

    public EntityMeta setFlyingWithElytra(boolean flying) {
        setFlag(SHARED_FLAGS, 7, flying);
        return this;
    }

    public boolean isFlyingWithElytra() {
        return getFlag(SHARED_FLAGS, 7);
    }

    public EntityMeta setAirSupply(int ticks) {
        set(AIR_SUPPLY, ticks);
        return this;
    }

    public int getAirSupply() {
        return get(AIR_SUPPLY);
    }

    public EntityMeta setCustomName(Component name) {
        set(CUSTOM_NAME, Optional.of(name != null ? name : Component.empty()));
        return this;
    }

    public Optional<Component> getCustomName() {
        return get(CUSTOM_NAME);
    }

    public Component customName() {
        return getCustomName().orElse(Component.empty());
    }

    public EntityMeta setCustomNameVisible(boolean visible) {
        set(CUSTOM_NAME_VISIBLE, visible);
        return this;
    }

    public boolean isCustomNameVisible() {
        return get(CUSTOM_NAME_VISIBLE);
    }

    public EntityMeta setSilent(boolean silent) {
        set(SILENT, silent);
        return this;
    }

    public boolean isSilent() {
        return get(SILENT);
    }

    public EntityMeta setNoGravity(boolean noGravity) {
        set(NO_GRAVITY, noGravity);
        return this;
    }

    public boolean hasNoGravity() {
        return get(NO_GRAVITY);
    }

    public EntityMeta setPose(Pose pose) {
        set(POSE, pose);
        return this;
    }

    public Pose getPose() {
        return get(POSE);
    }

    public EntityMeta setTicksFrozen(int ticks) {
        set(TICKS_FROZEN, ticks);
        return this;
    }

    public int getTicksFrozen() {
        return get(TICKS_FROZEN);
    }

}
