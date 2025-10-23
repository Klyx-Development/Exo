package org.klyx.exo.entities;

import net.minecraft.core.BlockPos;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.klyx.exo.data.attributes.EntityAttributes;
import org.klyx.exo.data.equipment.EntityEquipment;
import org.klyx.exo.data.keys.DataKeys;
import org.klyx.exo.entities.base.AbstractEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractLivingEntity extends AbstractEntity {

    private final EntityAttributes entityAttributes;
    private final EntityEquipment equipment;

    public AbstractLivingEntity(@NotNull EntityType entityType) {
        super(entityType);
        this.entityAttributes = new EntityAttributes(this);
        this.equipment = new EntityEquipment(this);
    }

    public AbstractLivingEntity(int entityId, @NotNull UUID entityUUID, @NotNull EntityType entityType) {
        super(entityId, entityUUID, entityType);
        this.entityAttributes = new EntityAttributes(this);
        this.equipment = new EntityEquipment(this);
    }

    @Override
    protected void initDefaultMetadata() {
        super.initDefaultMetadata();

        entityMetadata.set(DataKeys.HAND_STATES, (byte) 0);
        entityMetadata.set(DataKeys.HEALTH, 1.0f);
        entityMetadata.set(DataKeys.PARTICLES, List.of());
        entityMetadata.set(DataKeys.POTION_EFFECT_AMBIENT, false);
        entityMetadata.set(DataKeys.ARROWS_IN_ENTITY, 0);
        entityMetadata.set(DataKeys.BEE_STINGERS_IN_ENTITY, 0);
        entityMetadata.set(DataKeys.BED_POSITION, Optional.of(BlockPos.ZERO));
    }
}
