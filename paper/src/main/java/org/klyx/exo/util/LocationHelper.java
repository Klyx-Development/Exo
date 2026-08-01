package org.klyx.exo.util;

import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;

public class LocationHelper {

    public static Vec3 toVector3d(Location location) {
        return new Vec3(location.getX(), location.getY(), location.getZ());
    }

}
