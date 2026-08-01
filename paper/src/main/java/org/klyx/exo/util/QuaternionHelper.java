package org.klyx.exo.util;

import org.bukkit.util.Vector;
import org.joml.Quaternionf;

public final class QuaternionHelper {

    public static Quaternionf toQuaternion(Vector degrees) {
        return new Quaternionf().rotationXYZ(
                (float) Math.toRadians(degrees.getX()),
                (float) Math.toRadians(degrees.getY()),
                (float) Math.toRadians(degrees.getZ())
        );
    }

}
