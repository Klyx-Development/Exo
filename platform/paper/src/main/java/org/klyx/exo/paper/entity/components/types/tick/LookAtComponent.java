package org.klyx.exo.paper.entity.components.types.tick;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.klyx.exo.entity.ExoPos;
import org.klyx.exo.entity.ExoVec3d;
import org.klyx.exo.paper.util.PaperLocUtil;

public class LookAtComponent extends TickComponent {

    private final double radius;

    public LookAtComponent() {
        this(4);
    }

    public LookAtComponent(double radius) {
        this.radius = radius;
        double radiusSq = radius * radius;

        super((entity, _) -> {
            ExoPos entityPos = entity.getWorldStateManager().getWorldState().asExoPos();
            var entityWorld = entity.getWorldStateManager().getWorldState().currentWorld();
            Player nearest = null;
            double nearestDistanceSq = Double.MAX_VALUE;

            for (var uuid : entity.getActiveViewers()) {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null || !player.isOnline()) continue;

                Location playerLoc = player.getLocation();
                if (playerLoc.getWorld() == null || !PaperLocUtil.toExoWorld(playerLoc.getWorld()).equals(entityWorld)) continue;

                double dx = entityPos.x() - playerLoc.getX();
                double dy = entityPos.y() - playerLoc.getY();
                double dz = entityPos.z() - playerLoc.getZ();
                double distanceSq = dx * dx + dy * dy + dz * dz;
                if (distanceSq < nearestDistanceSq && distanceSq < radiusSq) {
                    nearest = player;
                    nearestDistanceSq = distanceSq;
                }
            }

            if (nearest != null) {
                Location target = nearest.getLocation();
                entity.getWorldStateManager().lookAt(new ExoVec3d(target.getX(), target.getY(), target.getZ()));
            }
        });
    }

    public double getRadius() {
        return radius;
    }
}
