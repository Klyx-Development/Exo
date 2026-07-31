package org.klyx.exo.entity.events;

import org.klyx.exo.event.Event;

public record EntityTickEvent(long currentTickCount) implements Event {
}
