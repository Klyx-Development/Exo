package org.klyx.exo.minestom;

import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.minestom.platform.MinestomExoPlatform;
import org.slf4j.Logger;

public final class ExoMinestom {

    private static @Nullable MinestomExoPlatform platform;

    public static void init() {
        if (platform != null) throw new IllegalStateException("Exo already initialized");

        platform = new MinestomExoPlatform();
        Exo.init(platform);
        platform.start();
    }

    public static void destroy() {
        if (platform == null) throw new IllegalStateException("Exo not initialized");

        platform.stop();
        Exo.destroy();
        platform = null;
    }

    public static Logger logger() {
        return Exo.logger();
    }

}
