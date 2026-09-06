package org.klyx.exo.paper.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.jspecify.annotations.Nullable;

public final class PaperRegistryAccess {

    private static RegistryAccess.@Nullable Frozen cached;

    public static RegistryAccess.Frozen get() {
        RegistryAccess.Frozen access = cached;
        if (access == null) {
            access = ((CraftServer) Bukkit.getServer()).getServer().registryAccess();
            cached = access;
        }

        return access;
    }

    public static HolderLookup.Provider asHolderLookup() {
        return get();
    }
}
