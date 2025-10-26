package org.klyx.exo.entities.impl;

import net.minecraft.world.entity.EquipmentSlot;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.klyx.exo.data.attributes.EntityAttributes;
import org.klyx.exo.data.equipment.EntityEquipment;
import org.klyx.exo.data.keys.DataKeys;

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
        entityMetadata.set(DataKeys.BED_POSITION, Optional.empty());
    }

    public void setHelmet(ItemStack itemStack) {
        this.getEquipment().setItem(EquipmentSlot.HEAD, itemStack);
    }

    public void setChestplate(ItemStack itemStack) {
        this.getEquipment().setItem(EquipmentSlot.CHEST, itemStack);
    }

    public void setLeggings(ItemStack itemStack) {
        this.getEquipment().setItem(EquipmentSlot.LEGS, itemStack);
    }

    public void setBoots(ItemStack itemStack) {
        this.getEquipment().setItem(EquipmentSlot.FEET, itemStack);
    }

    public void setMainHand(ItemStack itemStack) {
        this.getEquipment().setItem(EquipmentSlot.MAINHAND, itemStack);
    }

    public void setOffHand(ItemStack itemStack) {
        this.getEquipment().setItem(EquipmentSlot.OFFHAND, itemStack);
    }

    public void setAttribute(Attribute attribute, double value) {
        this.getEntityAttributes().setAttribute(attribute, value);
    }

    public void setAttribute(Attribute attribute, double value, List<AttributeModifier> modifiers) {
        this.getEntityAttributes().setAttribute(attribute, value, modifiers);
    }

    public EntityAttributes getEntityAttributes() {
        return entityAttributes;
    }

    public EntityEquipment getEquipment() {
        return equipment;
    }
}
