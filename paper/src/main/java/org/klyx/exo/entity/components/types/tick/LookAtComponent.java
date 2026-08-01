package org.klyx.exo.entity.components.types.tick;

import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LookAtComponent extends TickComponent {

    private final double radius;

    public LookAtComponent() {
        this(4);
    }

    public LookAtComponent(double radius) {
        this.radius = radius;
        double radiusSq = radius * radius;

        super((entity, event) -> {
            Location entityLoc = entity.getWorldStateManager().getWorldState().asLocation();
            Player nearest = null;
            double nearestDistanceSq = Double.MAX_VALUE;

            for (var uuid : entity.getActiveViewers()) {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null || !player.isOnline()) continue;

                Location playerLoc = player.getLocation();
                if (!playerLoc.getWorld().equals(entityLoc.getWorld())) continue;

                double distanceSq = entityLoc.distanceSquared(playerLoc);
                if (distanceSq < nearestDistanceSq && distanceSq < radiusSq) {
                    nearest = player;
                    nearestDistanceSq = distanceSq;
                }
            }

            if (nearest != null) {
                Location target = nearest.getLocation();
                entity.getWorldStateManager().lookAt(new Vec3(target.getX(), target.getY(), target.getZ()));
            }
        });
    }

    public double getRadius() {
        return radius;
    }
}