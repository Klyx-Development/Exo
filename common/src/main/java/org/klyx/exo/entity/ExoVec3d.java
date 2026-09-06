package org.klyx.exo.entity;

public record ExoVec3d(double x, double y, double z) {
    public static final ExoVec3d ZERO = new ExoVec3d(0, 0, 0);

    public ExoVec3d add(double dx, double dy, double dz) {
        return new ExoVec3d(x + dx, y + dy, z + dz);
    }
    public ExoVec3d add(ExoVec3d other) {
        return add(other.x, other.y, other.z);
    }
    public ExoVec3d subtract(ExoVec3d other) {
        return new ExoVec3d(x - other.x, y - other.y, z - other.z);
    }

    public double distanceSquared(ExoVec3d other) {
        double dx = x - other.x;
        double dy = y - other.y;
        double dz = z - other.z;
        return dx * dx + dy * dy + dz * dz;
    }
}
