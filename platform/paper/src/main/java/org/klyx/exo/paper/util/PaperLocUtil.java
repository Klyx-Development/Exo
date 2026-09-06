package org.klyx.exo.paper.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;
import org.bukkit.World;
import org.klyx.exo.entity.ExoPos;
import org.klyx.exo.entity.ExoVec3d;
import org.klyx.exo.entity.meta.ExoBlockPos;
import org.klyx.exo.paper.world.ExoPaperWorld;
import org.klyx.exo.world.ExoWorld;

public final class PaperLocUtil {

    private PaperLocUtil() {
    }

    public static ExoVec3d toExoVec3d(Vec3 vec) {
        return new ExoVec3d(vec.x(), vec.y(), vec.z());
    }

    public static Vec3 toNmsVec3(ExoVec3d vec) {
        return new Vec3(vec.x(), vec.y(), vec.z());
    }

    public static ExoPos toExoPos(Location location) {
        return new ExoPos(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public static Location toBukkitLocation(ExoWorld world, ExoPos pos) {
        return new Location(toBukkitWorld(world), pos.x(), pos.y(), pos.z(), pos.yaw(), pos.pitch());
    }

    public static ExoWorld toExoWorld(World world) {
        return ExoPaperWorld.of(world);
    }

    public static World toBukkitWorld(ExoWorld world) {
        return ((ExoPaperWorld) world).bukkit();
    }

    public static BlockPos toBlockPos(ExoBlockPos pos) {
        return new BlockPos(pos.x(), pos.y(), pos.z());
    }

    public static ExoBlockPos toExoBlockPos(BlockPos pos) {
        return new ExoBlockPos(pos.getX(), pos.getY(), pos.getZ());
    }

}
