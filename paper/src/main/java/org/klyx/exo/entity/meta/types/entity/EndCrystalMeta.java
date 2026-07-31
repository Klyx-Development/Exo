package org.klyx.exo.entity.meta.types.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataSerializers;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.types.EntityMeta;

import java.util.Optional;

public class EndCrystalMeta extends EntityMeta {

    private static final MetaAccessor<Optional<BlockPos>> BEAM_TARGET =
            new MetaAccessor<>(16, EntityDataSerializers.OPTIONAL_BLOCK_POS, Optional.empty());

    private static final MetaAccessor<Boolean> SHOW_BOTTOM =
            new MetaAccessor<>(17, EntityDataSerializers.BOOLEAN, true);

    public EndCrystalMeta setBeamTarget(BlockPos pos) {
        set(BEAM_TARGET, Optional.of(pos));
        return this;
    }

    public Optional<BlockPos> getBeamTarget() {
        return get(BEAM_TARGET);
    }

    public EndCrystalMeta setShowBottom(boolean showBottom) {
        set(SHOW_BOTTOM, showBottom);
        return this;
    }

    public boolean isShowingBottom() {
        return get(SHOW_BOTTOM);
    }

}
