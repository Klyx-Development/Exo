package org.klyx.exo.entity.events;

import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.event.Event;

public record EntityTickEvent(long currentTickCount, ExoEntity exoEntity) implements Event {
}
