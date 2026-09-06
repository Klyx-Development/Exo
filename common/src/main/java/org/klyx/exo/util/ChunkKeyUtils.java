package org.klyx.exo.util;

import org.klyx.exo.entity.ExoVec3d;

public class ChunkKeyUtils {

    public static long toLongKey(ExoVec3d position) {
        return toLongKey((int) Math.floor(position.x()) >> 4, (int) Math.floor(position.z()) >> 4);
    }

    public static long toLongKey(int chunkX, int chunkZ) {
        return ((long) chunkX << 32) | (chunkZ & 0xFFFFFFFFL);
    }

}
