package org.klyx.exo.event;

import org.bukkit.event.EventPriority;

public interface EventSubscription {
    EventPriority priority();
    void unsubscribe();
}
