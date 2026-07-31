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
        this.radius = radius; // THANK YOU JAVA 25
        super(event -> {
            Location entityLoc = event.exoEntity().getWorldStateManager().getWorldState().asLocation();
            Player nearest = null;
            double nearestDistance = Double.MAX_VALUE;

            for (var uuid : event.exoEntity().getActiveViewers()) {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null || !player.isOnline()) continue;

                Location playerLoc = player.getLocation();
                if (!playerLoc.getWorld().equals(entityLoc.getWorld())) continue;

                double distance = entityLoc.distanceSquared(player.getLocation());
                if (distance < nearestDistance && distance < radius * radius) {
                    nearest = player;
                    nearestDistance = distance;
                }
            }

            if (nearest != null) {
                Location target = nearest.getLocation();
                event.exoEntity().lookAt(new Vec3(target.getX(), target.getY(), target.getZ()));
            }
        });
    }

    public double getRadius() {
        return radius;
    }
}
