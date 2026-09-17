package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

import java.util.Optional;
import java.util.UUID;

public abstract class TameableAnimalMeta extends AnimalMeta {

    private static final MetaAccessor<Byte> FLAGS = new MetaAccessor<>(18, MetaType.BYTE, (byte) 0);
    private static final MetaAccessor<Optional<UUID>> OWNER_UUID = new MetaAccessor<>(19, MetaType.OPTIONAL_UUID, Optional.empty());

    public TameableAnimalMeta setSitting(boolean sitting) {
        setFlag(FLAGS, 0, sitting);
        return this;
    }

    public boolean isSitting() {
        return getFlag(FLAGS, 0);
    }

    public TameableAnimalMeta setTamed(boolean tamed) {
        setFlag(FLAGS, 2, tamed);
        return this;
    }

    public boolean isTamed() {
        return getFlag(FLAGS, 2);
    }

    public TameableAnimalMeta setOwner(Optional<UUID> owner) {
        set(OWNER_UUID, owner);
        return this;
    }

    public Optional<UUID> getOwner() {
        return get(OWNER_UUID);
    }
}
