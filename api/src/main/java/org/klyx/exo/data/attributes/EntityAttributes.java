package org.klyx.exo.data.attributes;

import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.bukkit.NamespacedKey;
import org.klyx.exo.entities.impl.AbstractEntity;
import org.klyx.exo.utils.PacketUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityAttributes {

    private final List<AttributeEntry> entries = new CopyOnWriteArrayList<>();
    private final AbstractEntity entity;

    public EntityAttributes(AbstractEntity entity) {
        this.entity = entity;
    }

    public void setAttribute(org.bukkit.attribute.Attribute attribute, double value, List<org.bukkit.attribute.AttributeModifier> modifiers) {
        entries.removeIf(entry -> entry.getAttribute().value() == attribute);

        AttributeEntry entry = new AttributeEntry(attribute, value);
        modifiers.forEach(entry::addModifier);

        entries.add(entry);
        refresh();
    }

    public void setAttribute(org.bukkit.attribute.Attribute attribute, double value, org.bukkit.attribute.AttributeModifier modifier) {
        setAttribute(attribute, value, Collections.singletonList(modifier));
    }

    public void setAttribute(org.bukkit.attribute.Attribute attribute, double value) {
        setAttribute(attribute, value, Collections.emptyList());
    }

    public void removeAttribute(org.bukkit.attribute.Attribute attribute, org.bukkit.attribute.AttributeModifier modifier) {
        entries.stream()
                .filter(entry -> entry.getAttribute().value() == attribute)
                .findFirst()
                .ifPresent(entry ->{
                    NamespacedKey modifierId = modifier.getKey();
                    entry.removeModifier(ResourceLocation.fromNamespaceAndPath(modifierId.getNamespace(), modifierId.getKey()));
                });
        refresh();
    }

    public void removeAttribute(org.bukkit.attribute.Attribute attribute) {
        entries.removeIf(entry -> entry.getAttribute().value() == attribute);
        refresh();
    }

    public void cleanup() {
        entries.clear();
        refresh();
    }

    public void refresh() {
        if (!entity.isAlive()) return;

        PacketUtil.sendPacket(entity.getViewers(), createPacket());
    }

    public ClientboundUpdateAttributesPacket createPacket() {
        return new ClientboundUpdateAttributesPacket(entity.getEntityId(), getEntries());
    }

    public List<AttributeInstance> getEntries() {
        return new ArrayList<>(entries);
    }
}
