package org.klyx.exo.paper.entity.components.types;

import org.bukkit.Location;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.ExoVec3d;
import org.klyx.exo.entity.components.EntityComponent;
import org.klyx.exo.entity.events.EntityDamageEvent;
import org.klyx.exo.paper.player.ExoPaperPlayer;

public class KnockbackComponent implements EntityComponent {

    private static final double DEFAULT_STRENGTH = 0.4;
    private static final double VERTICAL_LIMIT = 0.4;

    private @Nullable ExoEntity entity;
    private final double strength;

    public KnockbackComponent() {
        this(DEFAULT_STRENGTH);
    }

    public KnockbackComponent(double strength) {
        this.strength = strength;
    }

    @Override
    public void initialize(ExoEntity entity) {
        this.entity = entity;
        entity.eventBus().on(EntityDamageEvent.class, this::handleDamage);
    }

    private void handleDamage(EntityDamageEvent event) {
        if (entity == null || !entity.isSpawned()
                || event.isCancelled() || event.amount() <= 0.0F) return;

        double dx = 0.0;
        double dz = 0.0;

        if (event.attacker() instanceof ExoPaperPlayer attacker) {
            Location attackerLoc = attacker.bukkit().getLocation();
            ExoVec3d origin = entity.getLocation().toVec3d();
            dx = attackerLoc.getX() - origin.x();
            dz = attackerLoc.getZ() - origin.z();
        }

        double lengthSq = dx * dx + dz * dz;
        if (lengthSq > 1.0E-4) {
            double length = Math.sqrt(lengthSq);
            dx /= length;
            dz /= length;
        } else {
            dx = 0.0;
            dz = 0.0;
        }

        ExoVec3d velocity = entity.getVelocity();
        double vx = velocity != null ? velocity.x() : 0.0;
        double vy = velocity != null ? velocity.y() : 0.0;
        double vz = velocity != null ? velocity.z() : 0.0;

        double newVy = entity.isOnGround() ? Math.min(VERTICAL_LIMIT, vy / 2.0 + strength) : vy;
        entity.setVelocity(new ExoVec3d(vx / 2.0 - dx * strength, newVy, vz / 2.0 - dz * strength));
    }
}
