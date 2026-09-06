package org.klyx.exo.event;

public interface EventSubscription {
    EventPriority priority();
    void unsubscribe();
}
