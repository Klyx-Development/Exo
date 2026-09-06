package org.klyx.exo.common;

import org.klyx.exo.entity.ExoEntity;

public interface LeashDispatcher {
    void dispatchLink(ExoEntity entity, int holderEntityId);
}
