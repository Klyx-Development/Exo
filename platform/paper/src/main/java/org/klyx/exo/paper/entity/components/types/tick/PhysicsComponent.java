package org.klyx.exo.paper.entity.components.types.tick;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.ExoPos;
import org.klyx.exo.entity.ExoVec3d;
import org.klyx.exo.entity.components.EntityComponent;
import org.klyx.exo.entity.events.EntityTickEvent;
import org.klyx.exo.paper.util.PaperLocUtil;

public class PhysicsComponent implements EntityComponent {

    private static final double GRAVITY = 0.08;
    private static final double AIR_DRAG = 0.98;
    private static final double GROUND_FRICTION = 0.6;
    private static final double STOP_THRESHOLD = 0.003;

    private final TickComponent tickComponent = new TickComponent(this::onTick);

    @Override
    public void initialize(ExoEntity entity) {
        tickComponent.initialize(entity);
    }

    @Override
    public void destroy(ExoEntity entity) {
        tickComponent.destroy(entity);
    }

    private void onTick(ExoEntity entity, EntityTickEvent event) {
        if (!entity.isSpawned()) return;

        ExoVec3d velocity = entity.getVelocity();
        boolean idle = velocity == null
                || (Math.abs(velocity.x()) < STOP_THRESHOLD
                && Math.abs(velocity.y()) < STOP_THRESHOLD
                && Math.abs(velocity.z()) < STOP_THRESHOLD);
        if (idle && entity.isOnGround()) return;

        double vx = velocity != null ? velocity.x() : 0.0;
        double vy = velocity != null ? velocity.y() : 0.0;
        double vz = velocity != null ? velocity.z() : 0.0;

        vy -= GRAVITY;

        ExoPos currentPos = entity.getLocation();
        double newX = currentPos.x() + vx;
        double newY = currentPos.y() + vy;
        double newZ = currentPos.z() + vz;

        boolean grounded = false;
        if (vy <= 0.0) {
            World world = PaperLocUtil.toBukkitWorld(entity.getWorld());
            Block below = world.getBlockAt((int) Math.floor(newX), (int) Math.floor(newY - 0.01), (int) Math.floor(newZ));

            if (below.getType().isSolid()) {
                newY = Math.floor(newY) + 1.0;
                vy = 0.0;
                grounded = true;
            }
        }

        double horizontalDrag = grounded ? GROUND_FRICTION : AIR_DRAG;
        vx *= horizontalDrag;
        vz *= horizontalDrag;
        if (!grounded) vy *= AIR_DRAG;

        if (Math.abs(vx) < STOP_THRESHOLD) vx = 0.0;
        if (Math.abs(vy) < STOP_THRESHOLD) vy = 0.0;
        if (Math.abs(vz) < STOP_THRESHOLD) vz = 0.0;

        entity.setOnGround(grounded);
        entity.setLocation(entity.getWorld(), new ExoPos(newX, newY, newZ, currentPos.yaw(), currentPos.pitch()));
        entity.setVelocity(new ExoVec3d(vx, vy, vz));
    }
}
