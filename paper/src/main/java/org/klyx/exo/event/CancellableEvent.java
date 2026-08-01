package org.klyx.exo.event;

public class CancellableEvent implements Event {
    private boolean cancelled;

    public CancellableEvent() {
        this.cancelled = false;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
