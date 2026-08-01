package org.klyx.exo.data;

import java.util.concurrent.atomic.AtomicInteger;

public class EntityId {

    private static final AtomicInteger COUNTER = new AtomicInteger(10000000);

    public static int next() {
        return COUNTER.getAndIncrement();
    }

}
