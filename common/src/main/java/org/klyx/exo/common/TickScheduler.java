package org.klyx.exo.common;

public interface TickScheduler {
    interface Handle {
        void cancel();
    }

    Handle repeatEveryTick(Runnable task);
}
