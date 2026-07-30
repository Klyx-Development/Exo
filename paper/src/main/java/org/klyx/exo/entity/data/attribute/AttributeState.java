package org.klyx.exo.entity.data.attribute;

import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the state of an entity's attributes, including the current properties and the last synced properties.
 * Attributes are stored using maps for O(1) access and modification.
 */
public record AttributeState(
        @Unmodifiable Map<Holder<Attribute>, ClientboundUpdateAttributesPacket.AttributeSnapshot> currentProperties,
        @Unmodifiable @Nullable Map<Holder<Attribute>, ClientboundUpdateAttributesPacket.AttributeSnapshot> lastSyncedProperties
) {

    public AttributeState {
        currentProperties = Map.copyOf(currentProperties);
        lastSyncedProperties = lastSyncedProperties != null ? Map.copyOf(lastSyncedProperties) : null;
    }

    public AttributeState(List<ClientboundUpdateAttributesPacket.AttributeSnapshot> initialProperties) {
        this(toMap(initialProperties), null);
    }

    private static Map<Holder<Attribute>, ClientboundUpdateAttributesPacket.AttributeSnapshot> toMap(
            Iterable<ClientboundUpdateAttributesPacket.AttributeSnapshot> props) {
        Map<Holder<Attribute>, ClientboundUpdateAttributesPacket.AttributeSnapshot> map = new HashMap<>();
        for (ClientboundUpdateAttributesPacket.AttributeSnapshot prop : props) {
            map.put(prop.attribute(), prop);
        }
        return map;
    }

    public AttributeState with(Holder<Attribute> attribute, double value, List<AttributeModifier> modifiers) {
        Map<Holder<Attribute>, ClientboundUpdateAttributesPacket.AttributeSnapshot> newProperties = new HashMap<>(this.currentProperties);
        newProperties.put(attribute, new ClientboundUpdateAttributesPacket.AttributeSnapshot(attribute, value, modifiers));
        return new AttributeState(newProperties, this.lastSyncedProperties);
    }

    public AttributeState with(Holder<Attribute> attribute, double value, AttributeModifier modifier) {
        return with(attribute, value, Collections.singletonList(modifier));
    }

    public AttributeState with(Holder<Attribute> attribute, double value) {
        return with(attribute, value, Collections.emptyList());
    }

    public AttributeState withAll(Iterable<ClientboundUpdateAttributesPacket.AttributeSnapshot> newProps) {
        Map<Holder<Attribute>, ClientboundUpdateAttributesPacket.AttributeSnapshot> newProperties = new HashMap<>(this.currentProperties);
        for (ClientboundUpdateAttributesPacket.AttributeSnapshot newProp : newProps) {
            newProperties.put(newProp.attribute(), newProp);
        }
        return new AttributeState(newProperties, this.lastSyncedProperties);
    }

    public AttributeState withAll(ClientboundUpdateAttributesPacket.AttributeSnapshot... newProps) {
        return withAll(Arrays.asList(newProps));
    }

    public AttributeState without(Holder<Attribute> attribute, AttributeModifier modifier) {
        ClientboundUpdateAttributesPacket.AttributeSnapshot existing = this.currentProperties.get(attribute);
        if (existing == null) return this;

        List<AttributeModifier> newModifiers = new ArrayList<>(existing.modifiers());
        boolean removed = newModifiers.remove(modifier);

        if (!removed) return this;

        Map<Holder<Attribute>, ClientboundUpdateAttributesPacket.AttributeSnapshot> newProperties = new HashMap<>(this.currentProperties);
        if (newModifiers.isEmpty()) {
            newProperties.remove(attribute);
        } else {
            newProperties.put(attribute, new ClientboundUpdateAttributesPacket.AttributeSnapshot(attribute, existing.base(), newModifiers));
        }

        return new AttributeState(newProperties, this.lastSyncedProperties);
    }

    public AttributeState without(Holder<Attribute> attribute) {
        Map<Holder<Attribute>, ClientboundUpdateAttributesPacket.AttributeSnapshot> newProperties = new HashMap<>(this.currentProperties);
        newProperties.remove(attribute);
        return new AttributeState(newProperties, this.lastSyncedProperties);
    }

    public AttributeState withoutAll(Collection<Holder<Attribute>> attributes) {
        Map<Holder<Attribute>, ClientboundUpdateAttributesPacket.AttributeSnapshot> newProperties = new HashMap<>(this.currentProperties);
        for (Holder<Attribute> attr : attributes) {
            newProperties.remove(attr);
        }
        return new AttributeState(newProperties, this.lastSyncedProperties);
    }

    public AttributeState withoutAll(Holder<Attribute>... attributesToRemove) {
        return withoutAll(Arrays.asList(attributesToRemove));
    }

    public boolean needsFullSync() {
        return this.lastSyncedProperties == null;
    }

    public boolean hasChanged() {
        if (needsFullSync()) return true;
        if (this.currentProperties.size() != this.lastSyncedProperties.size()) return true;

        for (Map.Entry<Holder<Attribute>, ClientboundUpdateAttributesPacket.AttributeSnapshot> entry : this.currentProperties.entrySet()) {
            ClientboundUpdateAttributesPacket.AttributeSnapshot synced = this.lastSyncedProperties.get(entry.getKey());
            if (!entry.getValue().equals(synced)) {
                return true;
            }
        }
        return false;
    }

    public List<ClientboundUpdateAttributesPacket.AttributeSnapshot> dirtyProperties() {
        if (needsFullSync()) {
            return new ArrayList<>(this.currentProperties.values());
        }

        List<ClientboundUpdateAttributesPacket.AttributeSnapshot> dirty = new ArrayList<>();
        for (Map.Entry<Holder<Attribute>, ClientboundUpdateAttributesPacket.AttributeSnapshot> entry : this.currentProperties.entrySet()) {
            ClientboundUpdateAttributesPacket.AttributeSnapshot current = entry.getValue();
            ClientboundUpdateAttributesPacket.AttributeSnapshot synced = this.lastSyncedProperties.get(entry.getKey());

            if (!current.equals(synced)) {
                dirty.add(current);
            }
        }
        return dirty;
    }

    public AttributeState sync() {
        return new AttributeState(this.currentProperties, this.currentProperties);
    }

    public ClientboundUpdateAttributesPacket createPacket(int entityId) {
        List<AttributeInstance> instances = new ArrayList<>();

        for (ClientboundUpdateAttributesPacket.AttributeSnapshot snapshot : dirtyProperties()) {
            AttributeInstance instance = new AttributeInstance(snapshot.attribute(), ai -> {});
            instance.setBaseValue(snapshot.base());
            for (AttributeModifier modifier : snapshot.modifiers()) {
                instance.addTransientModifier(modifier);
            }
            instances.add(instance);
        }

        return new ClientboundUpdateAttributesPacket(entityId, instances);
    }
}