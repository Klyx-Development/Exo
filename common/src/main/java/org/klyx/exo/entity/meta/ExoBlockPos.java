package org.klyx.exo.entity.meta;

public record ExoBlockPos(int x, int y, int z) {
    public static final ExoBlockPos ZERO = new ExoBlockPos(0, 0, 0);
}
