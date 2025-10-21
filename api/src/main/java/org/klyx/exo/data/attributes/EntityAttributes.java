package org.klyx.exo.data.attributes;

import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.klyx.exo.entities.AbstractEntity;
import org.klyx.exo.utils.PacketUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityAttributes {

    private final List<AttributeEntry> entries = new CopyOnWriteArrayList<>();
    private final AbstractEntity entity;

    public EntityAttributes(AbstractEntity entity) {
        this.entity = entity;
    }

    public void setAttribute(Attribute attribute, double value, List<AttributeModifier> modifiers) {
        entries.removeIf(entry -> entry.getAttribute().value() == attribute);

        AttributeEntry entry = new AttributeEntry(attribute, value);
        modifiers.forEach(entry::addModifier);

        entries.add(entry);
        refresh();
    }

    public void setAttribute(Attribute attribute, double value, AttributeModifier modifier) {
        setAttribute(attribute, value, Collections.singletonList(modifier));
    }

    public void setAttribute(Attribute attribute, double value) {
        setAttribute(attribute, value, Collections.emptyList());
    }

    public void removeAttribute(Attribute attribute, AttributeModifier modifier) {
        entries.stream()
                .filter(entry -> entry.getAttribute().value() == attribute)
                .findFirst()
                .ifPresent(entry ->{
                    ResourceLocation modifierId = modifier.id();
                    entry.removeModifier(modifierId);
                });
        refresh();
    }

    public void removeAttribute(Attribute attribute) {
        entries.removeIf(entry -> entry.getAttribute().value() == attribute);
        refresh();
    }

    public void cleanup() {
        entries.clear();
        refresh();
    }

    public void refresh() {
        if (!entity.isSpawned()) return;

        PacketUtil.sendPacket(entity.getViewers(), createPacket());
    }

    public ClientboundUpdateAttributesPacket createPacket() {
        return new ClientboundUpdateAttributesPacket(entity.getEntityId(), getEntries());
    }

    public List<AttributeInstance> getEntries() {
        return new ArrayList<>(entries);
    }
}
