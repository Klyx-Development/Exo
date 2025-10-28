package org.klyx.exo.entities.impl;

import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.klyx.exo.data.keys.DataKeys;

public abstract class AbstractMob extends AbstractLivingEntity {
    public AbstractMob(@NotNull EntityType entityType) {
        super(entityType);
    }

    @Override
    protected void initDefaultMetadata() {
        super.initDefaultMetadata();

        entityMetadata.set(DataKeys.Mob.MOB_FLAGS);
    }
}
