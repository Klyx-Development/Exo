package org.klyx.exo.entity.meta.types.entity.living.avatar;

import com.destroystokyo.paper.profile.CraftPlayerProfile;
import com.destroystokyo.paper.profile.PlayerProfile;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.item.component.ResolvableProfile;
import org.bukkit.entity.Player;
import org.klyx.exo.entity.meta.impl.MetaAccessor;

import java.util.Optional;

public class MannequinMeta extends AvatarMeta {

    private static final MetaAccessor<ResolvableProfile> PROFILE = new MetaAccessor<>(17, EntityDataSerializers.RESOLVABLE_PROFILE,
            ResolvableProfile.createUnresolved(""));
    private static final MetaAccessor<Boolean> IMMOVABLE = new MetaAccessor<>(18, EntityDataSerializers.BOOLEAN, false);
    private static final MetaAccessor<Optional<Component>> DESCRIPTION = new MetaAccessor<>(19, EntityDataSerializers.OPTIONAL_COMPONENT, Optional.of(Component.empty()));

    public MannequinMeta setProfile(ResolvableProfile profile) {
        set(PROFILE, profile);
        return this;
    }

    public MannequinMeta setProfile(PlayerProfile profile) {
        return setProfile(((CraftPlayerProfile) profile).buildResolvableProfile());
    }

    public MannequinMeta setProfile(Player player) {
        return setProfile(player.getPlayerProfile());
    }

    public ResolvableProfile getProfile() {
        return get(PROFILE);
    }

    public MannequinMeta setImmovable(boolean immovable) {
        set(IMMOVABLE, immovable);
        return this;
    }

    public boolean isImmovable() {
        return get(IMMOVABLE);
    }

    public MannequinMeta setDescription(Component description) {
        set(DESCRIPTION, Optional.ofNullable(description));
        return this;
    }

    public Optional<Component> getDescription() {
        return get(DESCRIPTION);
    }

}