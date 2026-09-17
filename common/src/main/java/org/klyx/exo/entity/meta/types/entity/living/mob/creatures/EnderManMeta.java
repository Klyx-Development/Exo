package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.ExoBlockState;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

import java.util.Optional;

public final class EnderManMeta extends CreatureMeta {

    private static final MetaAccessor<Optional<ExoBlockState>> CARRIED_BLOCK =
            new MetaAccessor<>(16, MetaType.OPTIONAL_BLOCK_STATE, Optional.empty());
    private static final MetaAccessor<Boolean> IS_SCREAMING = new MetaAccessor<>(17, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Boolean> IS_STARING = new MetaAccessor<>(18, MetaType.BOOLEAN, false);

    public EnderManMeta setCarriedBlock(Optional<ExoBlockState> block) {
        set(CARRIED_BLOCK, block);
        return this;
    }

    public Optional<ExoBlockState> getCarriedBlock() {
        return get(CARRIED_BLOCK);
    }

    public EnderManMeta setScreaming(boolean screaming) {
        set(IS_SCREAMING, screaming);
        return this;
    }

    public boolean isScreaming() {
        return get(IS_SCREAMING);
    }

    public EnderManMeta setStaring(boolean staring) {
        set(IS_STARING, staring);
        return this;
    }

    public boolean isStaring() {
        return get(IS_STARING);
    }
}
