package org.klyx.exo.common;

import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.data.world.EntityWorldState;

public interface MovementDispatcher {
    void sendTeleport(ExoEntity entity, EntityWorldState state);
    void sendPosRot(ExoEntity entity, EntityWorldState state);
    void sendPos(ExoEntity entity, EntityWorldState state);
    void sendRot(ExoEntity entity, EntityWorldState state);
    void sendHeadRot(ExoEntity entity, EntityWorldState state);
    void sendVelocity(ExoEntity entity, EntityWorldState state);
}
