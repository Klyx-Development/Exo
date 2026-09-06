package org.klyx.exo.paper.meta;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.common.collect.HashMultimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.world.item.component.ResolvableProfile;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.meta.ExoProfile;
import org.klyx.exo.entity.meta.ExoProfileProperty;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class PaperProfiles {

    public static ResolvableProfile toNms(ExoProfile profile) {
        return ResolvableProfile.createResolved(toGameProfile(profile));
    }

    public static GameProfile toGameProfile(ExoProfile profile) {
        UUID id = profile.id() != null ? profile.id() : offlineUuid(profile.name());
        String name = profile.name() != null ? profile.name() : "";

        var multimap = HashMultimap.<String, Property>create();
        for (ExoProfileProperty property : profile.properties()) {
            multimap.put(property.name(), new Property(property.name(), property.value(), property.signature()));
        }

        return new GameProfile(id, name, new PropertyMap(multimap));
    }

    public static ExoProfile fromNms(GameProfile profile) {
        List<ExoProfileProperty> properties = new ArrayList<>();
        for (Property property : profile.properties().values()) {
            properties.add(new ExoProfileProperty(property.name(), property.value(), property.signature()));
        }

        return new ExoProfile(profile.id(), profile.name(), properties);
    }

    public static ExoProfile fromBukkit(PlayerProfile profile) {
        List<ExoProfileProperty> properties = new ArrayList<>();
        for (var property : profile.getProperties()) {
            properties.add(new ExoProfileProperty(property.getName(), property.getValue(), property.getSignature()));
        }

        return new ExoProfile(profile.getId(), profile.getName(), properties);
    }

    public static ExoProfile fromBukkit(Player player) {
        return fromBukkit(player.getPlayerProfile());
    }

    private static UUID offlineUuid(@Nullable String name) {
        return name != null ? UUID.nameUUIDFromBytes((name).getBytes(StandardCharsets.UTF_8)) : new UUID(0, 0);
    }
}
