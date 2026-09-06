package org.klyx.exo.paper.entity;

import org.bukkit.Registry;
import org.bukkit.craftbukkit.entity.CraftEntityType;
import org.bukkit.entity.EntityType;
import org.klyx.exo.entity.data.ExoEntityType;
import org.klyx.exo.paper.util.PaperKey;

public final class PaperEntityTypes {

    private PaperEntityTypes() {}

    public static ExoEntityType toExo(EntityType bukkitType) {
        return new ExoEntityType(PaperKey.from(bukkitType.getKey()));
    }

    public static EntityType toBukkit(ExoEntityType exoType) {
        PaperKey paperKey = PaperKey.from(exoType.key());
        EntityType type = Registry.ENTITY_TYPE.get(paperKey.namespacedKey());
        if (type == null) throw new IllegalArgumentException("Unknown entity type: " + exoType.key());
        return type;
    }

    public static net.minecraft.world.entity.EntityType<?> toNms(ExoEntityType exoType) {
        return CraftEntityType.bukkitToMinecraft(toBukkit(exoType));
    }
}
