package org.klyx.exo.entity.meta.types.entity;

import org.klyx.exo.entity.meta.Direction;
import org.klyx.exo.entity.meta.ExoVariant;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.entity.meta.types.EntityMeta;
import org.klyx.exo.util.Key;

public final class PaintingMeta extends EntityMeta {

    private static final MetaAccessor<Direction> DIRECTION = new MetaAccessor<>(8, MetaType.DIRECTION, Direction.SOUTH);
    private static final MetaAccessor<ExoVariant> VARIANT =
            new MetaAccessor<>(9, MetaType.PAINTING_VARIANT, new ExoVariant(Key.of("kebab")));

    public PaintingMeta setDirection(Direction direction) {
        set(DIRECTION, direction);
        return this;
    }

    public Direction getDirection() {
        return get(DIRECTION);
    }

    public PaintingMeta setVariant(ExoVariant variant) {
        set(VARIANT, variant);
        return this;
    }

    public ExoVariant getVariant() {
        return get(VARIANT);
    }
}
