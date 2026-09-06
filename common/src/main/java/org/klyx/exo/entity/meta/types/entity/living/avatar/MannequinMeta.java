package org.klyx.exo.entity.meta.types.entity.living.avatar;

import net.kyori.adventure.text.Component;
import org.klyx.exo.entity.meta.ExoProfile;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

import java.util.Optional;

public class MannequinMeta extends AvatarMeta {

    private static final MetaAccessor<ExoProfile> PROFILE = new MetaAccessor<>(17, MetaType.RESOLVABLE_PROFILE,
            ExoProfile.EMPTY);
    private static final MetaAccessor<Boolean> IMMOVABLE = new MetaAccessor<>(18, MetaType.BOOLEAN, false);
    private static final MetaAccessor<Optional<Component>> DESCRIPTION = new MetaAccessor<>(19, MetaType.OPTIONAL_COMPONENT, Optional.of(Component.empty()));

    public MannequinMeta setProfile(ExoProfile profile) {
        set(PROFILE, profile);
        return this;
    }

    public ExoProfile getProfile() {
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
