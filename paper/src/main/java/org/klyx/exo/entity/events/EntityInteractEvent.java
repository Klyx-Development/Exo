package org.klyx.exo.entity.events;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;
import org.bukkit.entity.Player;
import org.klyx.exo.event.Event;

public record EntityInteractEvent(Player player, InteractionHand hand, Vec3 targetOffset,
                                  boolean sneaking) implements Event {

    /**
     * Offset from the target entity's position to the clicked point;
     * NOT an absolute world coordinate. Add this to the entity's position
     * to get the actual clicked world location.
     */
    @Override
    public Vec3 targetOffset() {
        return targetOffset;
    }

    public Vec3 absoluteTargetPos(Vec3 entityPos) {
        return entityPos.add(targetOffset);
    }
}
