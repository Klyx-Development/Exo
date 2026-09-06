package org.klyx.exo.minestom.meta;

import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.ResolvableProfile;
import org.klyx.exo.entity.meta.ExoProfile;
import org.klyx.exo.entity.meta.ExoProfileProperty;

import java.util.ArrayList;
import java.util.List;

public final class MinestomProfiles {

    public static ResolvableProfile toMinestom(ExoProfile profile) {
        List<GameProfile.Property> properties = new ArrayList<>(profile.properties().size());
        for (ExoProfileProperty property : profile.properties()) {
            properties.add(new GameProfile.Property(property.name(), property.value(), property.signature()));
        }

        return new ResolvableProfile(new ResolvableProfile.Partial(profile.name(), profile.id(), properties));
    }

    public static ExoProfile fromMinestom(GameProfile profile) {
        List<ExoProfileProperty> properties = new ArrayList<>(profile.properties().size());
        for (GameProfile.Property property : profile.properties()) {
            properties.add(new ExoProfileProperty(property.name(), property.value(), property.signature()));
        }

        return new ExoProfile(profile.uuid(), profile.name(), properties);
    }
}
