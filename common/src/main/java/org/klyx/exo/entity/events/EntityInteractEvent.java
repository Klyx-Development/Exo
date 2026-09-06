package org.klyx.exo.entity.events;

import org.klyx.exo.entity.ExoVec3d;
import org.klyx.exo.entity.InteractionHand;
import org.klyx.exo.event.Event;
import org.klyx.exo.player.ExoPlayer;

/**
 * @param targetOffset Offset from the target entity's position to the clicked point;
 *                      NOT an absolute world coordinate. Add this to the entity's position
 *                      to get the actual clicked world location.
 */
public record EntityInteractEvent(ExoPlayer player, InteractionHand hand, ExoVec3d targetOffset,
                                  boolean sneaking) implements Event {

    public ExoVec3d absoluteTargetPos(ExoVec3d entityPos) {
        return entityPos.add(targetOffset);
    }
}
