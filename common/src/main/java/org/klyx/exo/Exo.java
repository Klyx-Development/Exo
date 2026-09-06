package org.klyx.exo;

import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.EntityManager;
import org.klyx.exo.common.ExoPlatform;
import org.slf4j.Logger;

public final class Exo {

    private static volatile @Nullable ExoPlatform platform;
    private static final EntityManager ENTITY_MANAGER = new EntityManager();

    private Exo() { }

    public static void init(ExoPlatform platform) {
        if (Exo.platform != null) throw new IllegalStateException("Exo already initialized");
        Exo.platform = platform;
    }

    public static void destroy() {
        if (Exo.platform == null) throw new IllegalStateException("Exo not initialized");
        ENTITY_MANAGER.destroy();
        platform = null;
    }

    public static ExoPlatform platform() {
        ExoPlatform p = platform;
        if (p == null) throw new IllegalStateException("Exo not initialized");
        return p;
    }

    public static EntityManager entityManager() {
        return ENTITY_MANAGER;
    }

    public static Logger logger() {
        return platform().logger();
    }

}
