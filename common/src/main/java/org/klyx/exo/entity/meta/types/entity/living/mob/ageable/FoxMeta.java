package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

import java.util.Optional;
import java.util.UUID;

public final class FoxMeta extends AnimalMeta {

    private static final MetaAccessor<Integer> VARIANT = new MetaAccessor<>(18, MetaType.INT, 0);
    private static final MetaAccessor<Byte> FLAGS = new MetaAccessor<>(19, MetaType.BYTE, (byte) 0);
    private static final MetaAccessor<Optional<UUID>> FIRST_TRUSTED = new MetaAccessor<>(20, MetaType.OPTIONAL_UUID, Optional.empty());
    private static final MetaAccessor<Optional<UUID>> SECOND_TRUSTED = new MetaAccessor<>(21, MetaType.OPTIONAL_UUID, Optional.empty());

    public FoxMeta setVariant(int variant) {
        set(VARIANT, variant);
        return this;
    }

    public int getVariant() {
        return get(VARIANT);
    }

    public FoxMeta setSitting(boolean sitting) {
        setFlag(FLAGS, 0, sitting);
        return this;
    }

    public boolean isSitting() {
        return getFlag(FLAGS, 0);
    }

    public FoxMeta setCrouching(boolean crouching) {
        setFlag(FLAGS, 2, crouching);
        return this;
    }

    public boolean isCrouching() {
        return getFlag(FLAGS, 2);
    }

    public FoxMeta setInterested(boolean interested) {
        setFlag(FLAGS, 3, interested);
        return this;
    }

    public boolean isInterested() {
        return getFlag(FLAGS, 3);
    }

    public FoxMeta setPouncing(boolean pouncing) {
        setFlag(FLAGS, 4, pouncing);
        return this;
    }

    public boolean isPouncing() {
        return getFlag(FLAGS, 4);
    }

    public FoxMeta setSleeping(boolean sleeping) {
        setFlag(FLAGS, 5, sleeping);
        return this;
    }

    public boolean isSleeping() {
        return getFlag(FLAGS, 5);
    }

    public FoxMeta setFaceplanted(boolean faceplanted) {
        setFlag(FLAGS, 6, faceplanted);
        return this;
    }

    public boolean isFaceplanted() {
        return getFlag(FLAGS, 6);
    }

    public FoxMeta setDefending(boolean defending) {
        setFlag(FLAGS, 7, defending);
        return this;
    }

    public boolean isDefending() {
        return getFlag(FLAGS, 7);
    }

    public FoxMeta setFirstTrusted(Optional<UUID> uuid) {
        set(FIRST_TRUSTED, uuid);
        return this;
    }

    public Optional<UUID> getFirstTrusted() {
        return get(FIRST_TRUSTED);
    }

    public FoxMeta setSecondTrusted(Optional<UUID> uuid) {
        set(SECOND_TRUSTED, uuid);
        return this;
    }

    public Optional<UUID> getSecondTrusted() {
        return get(SECOND_TRUSTED);
    }
}
