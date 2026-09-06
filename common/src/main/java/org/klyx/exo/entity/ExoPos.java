package org.klyx.exo.entity;

public record ExoPos(double x, double y, double z, float yaw, float pitch) {
    public ExoVec3d toVec3d() {
        return new ExoVec3d(x, y, z);
    }
    public ExoPos withPos(ExoVec3d pos) {
        return new ExoPos(pos.x(), pos.y(), pos.z(), yaw, pitch);
    }
    public ExoPos withRotation(float yaw, float pitch) {
        return new ExoPos(x, y, z, yaw, pitch);
    }
}
