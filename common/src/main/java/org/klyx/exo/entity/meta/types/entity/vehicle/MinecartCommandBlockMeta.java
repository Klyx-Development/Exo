package org.klyx.exo.entity.meta.types.entity.vehicle;

import net.kyori.adventure.text.Component;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class MinecartCommandBlockMeta extends AbstractMinecartMeta {

    private static final MetaAccessor<String> COMMAND = new MetaAccessor<>(13, MetaType.STRING, "");
    private static final MetaAccessor<Component> LAST_OUTPUT = new MetaAccessor<>(14, MetaType.COMPONENT, Component.empty());

    public MinecartCommandBlockMeta setCommand(String command) {
        set(COMMAND, command);
        return this;
    }

    public String getCommand() {
        return get(COMMAND);
    }

    public MinecartCommandBlockMeta setLastOutput(Component output) {
        set(LAST_OUTPUT, output);
        return this;
    }

    public Component getLastOutput() {
        return get(LAST_OUTPUT);
    }
}
