package org.klyx.exo.utils;

import org.joml.Quaternionf;

public class MathUtil {

    public static Quaternionf eulerToQuaternion(double pitch, double yaw, double roll) {
        double p = Math.toRadians(pitch);
        double y = Math.toRadians(yaw);
        double r = Math.toRadians(roll);

        double cy = Math.cos(y * 0.5);
        double sy = Math.sin(y * 0.5);
        double cp = Math.cos(p * 0.5);
        double sp = Math.sin(p * 0.5);
        double cr = Math.cos(r * 0.5);
        double sr = Math.sin(r * 0.5);

        float w = (float)(cr * cp * cy + sr * sp * sy);
        float x = (float)(sr * cp * cy - cr * sp * sy);
        float y1 = (float)(cr * sp * cy + sr * cp * sy);
        float z = (float)(cr * cp * sy - sr * sp * cy);

        return new Quaternionf(x, y1, z, w);
    }

}
