package org.klyx.exo.minestom.util;

import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import org.klyx.exo.entity.ExoPos;
import org.klyx.exo.entity.ExoVec3d;

public final class MinestomLocationHelper {

    public static Pos toPos(ExoPos pos) {
        return new Pos(pos.x(), pos.y(), pos.z(), pos.yaw(), pos.pitch());
    }

    public static ExoPos toExoPos(Pos pos) {
        return new ExoPos(pos.x(), pos.y(), pos.z(), pos.yaw(), pos.pitch());
    }

    public static Vec toVec(ExoVec3d vec) {
        return new Vec(vec.x(), vec.y(), vec.z());
    }

    public static ExoVec3d toExoVec3d(Point point) {
        return new ExoVec3d(point.x(), point.y(), point.z());
    }
}
