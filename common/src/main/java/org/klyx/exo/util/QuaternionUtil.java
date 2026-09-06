package org.klyx.exo.util;

import org.joml.Quaternionf;
import org.joml.Vector3fc;

public final class QuaternionUtil {

    public static Quaternionf toQuaternion(Vector3fc degrees) {
        return new Quaternionf().rotationXYZ(
                (float) Math.toRadians(degrees.x()),
                (float) Math.toRadians(degrees.y()),
                (float) Math.toRadians(degrees.z())
        );
    }

}
