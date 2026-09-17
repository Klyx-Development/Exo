package org.klyx.exo.entity.meta.types.entity.living.mob.golem;

import org.klyx.exo.entity.meta.Direction;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class ShulkerMeta extends AbstractGolemMeta {

    private static final MetaAccessor<Direction> ATTACH_FACE = new MetaAccessor<>(16, MetaType.DIRECTION, Direction.DOWN);
    private static final MetaAccessor<Byte> SHIELD_HEIGHT = new MetaAccessor<>(17, MetaType.BYTE, (byte) 0);
    private static final MetaAccessor<Byte> COLOR = new MetaAccessor<>(18, MetaType.BYTE, (byte) 16);

    public ShulkerMeta setAttachFace(Direction face) {
        set(ATTACH_FACE, face);
        return this;
    }

    public Direction getAttachFace() {
        return get(ATTACH_FACE);
    }

    public ShulkerMeta setShieldHeight(byte height) {
        set(SHIELD_HEIGHT, height);
        return this;
    }

    public byte getShieldHeight() {
        return get(SHIELD_HEIGHT);
    }

    public ShulkerMeta setColor(byte color) {
        set(COLOR, color);
        return this;
    }

    public byte getColor() {
        return get(COLOR);
    }
}
