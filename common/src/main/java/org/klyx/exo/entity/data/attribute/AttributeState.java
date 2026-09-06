package org.klyx.exo.entity.data.attribute;

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
 */
public record AttributeState(
        @Unmodifiable Map<ExoAttribute, ExoAttributeSnapshot> currentProperties,
        @Unmodifiable @Nullable Map<ExoAttribute, ExoAttributeSnapshot> lastSyncedProperties
) {

    public AttributeState {
        currentProperties = Map.copyOf(currentProperties);
        lastSyncedProperties = lastSyncedProperties != null ? Map.copyOf(lastSyncedProperties) : null;
    }

    public AttributeState(List<ExoAttributeSnapshot> initialProperties) {
        this(toMap(initialProperties), null);
    }

    private static Map<ExoAttribute, ExoAttributeSnapshot> toMap(Iterable<ExoAttributeSnapshot> props) {
        Map<ExoAttribute, ExoAttributeSnapshot> map = new HashMap<>();
        for (ExoAttributeSnapshot prop : props) {
            map.put(prop.attribute(), prop);
        }
        return map;
    }

    public AttributeState with(ExoAttribute attribute, double value, List<ExoAttributeModifier> modifiers) {
        Map<ExoAttribute, ExoAttributeSnapshot> newProperties = new HashMap<>(this.currentProperties);
        newProperties.put(attribute, new ExoAttributeSnapshot(attribute, value, modifiers));
        return new AttributeState(newProperties, this.lastSyncedProperties);
    }

    public AttributeState with(ExoAttribute attribute, double value, ExoAttributeModifier modifier) {
        return with(attribute, value, Collections.singletonList(modifier));
    }

    public AttributeState with(ExoAttribute attribute, double value) {
        return with(attribute, value, Collections.emptyList());
    }

    public AttributeState withAll(Iterable<ExoAttributeSnapshot> newProps) {
        Map<ExoAttribute, ExoAttributeSnapshot> newProperties = new HashMap<>(this.currentProperties);
        for (ExoAttributeSnapshot newProp : newProps) {
            newProperties.put(newProp.attribute(), newProp);
        }
        return new AttributeState(newProperties, this.lastSyncedProperties);
    }

    public AttributeState withAll(ExoAttributeSnapshot... newProps) {
        return withAll(Arrays.asList(newProps));
    }

    public AttributeState without(ExoAttribute attribute, ExoAttributeModifier modifier) {
        ExoAttributeSnapshot existing = this.currentProperties.get(attribute);
        if (existing == null) return this;

        List<ExoAttributeModifier> newModifiers = new ArrayList<>(existing.modifiers());
        boolean removed = newModifiers.remove(modifier);

        if (!removed) return this;

        Map<ExoAttribute, ExoAttributeSnapshot> newProperties = new HashMap<>(this.currentProperties);
        if (newModifiers.isEmpty()) {
            newProperties.remove(attribute);
        } else {
            newProperties.put(attribute, new ExoAttributeSnapshot(attribute, existing.base(), newModifiers));
        }

        return new AttributeState(newProperties, this.lastSyncedProperties);
    }

    public AttributeState without(ExoAttribute attribute) {
        Map<ExoAttribute, ExoAttributeSnapshot> newProperties = new HashMap<>(this.currentProperties);
        newProperties.remove(attribute);
        return new AttributeState(newProperties, this.lastSyncedProperties);
    }

    public AttributeState withoutAll(Collection<ExoAttribute> attributes) {
        Map<ExoAttribute, ExoAttributeSnapshot> newProperties = new HashMap<>(this.currentProperties);
        for (ExoAttribute attr : attributes) {
            newProperties.remove(attr);
        }
        return new AttributeState(newProperties, this.lastSyncedProperties);
    }

    public AttributeState withoutAll(ExoAttribute... attributesToRemove) {
        return withoutAll(Arrays.asList(attributesToRemove));
    }

    public boolean needsFullSync() {
        return this.lastSyncedProperties == null;
    }

    public boolean hasChanged() {
        if (needsFullSync()) return true;
        if (this.currentProperties.size() != this.lastSyncedProperties.size()) return true;

        for (Map.Entry<ExoAttribute, ExoAttributeSnapshot> entry : this.currentProperties.entrySet()) {
            ExoAttributeSnapshot synced = this.lastSyncedProperties.get(entry.getKey());
            if (!entry.getValue().equals(synced)) {
                return true;
            }
        }
        return false;
    }

    public List<ExoAttributeSnapshot> dirtyProperties() {
        if (needsFullSync()) {
            return new ArrayList<>(this.currentProperties.values());
        }

        List<ExoAttributeSnapshot> dirty = new ArrayList<>();
        for (Map.Entry<ExoAttribute, ExoAttributeSnapshot> entry : this.currentProperties.entrySet()) {
            ExoAttributeSnapshot current = entry.getValue();
            ExoAttributeSnapshot synced = this.lastSyncedProperties.get(entry.getKey());

            if (!current.equals(synced)) {
                dirty.add(current);
            }
        }
        return dirty;
    }

    public AttributeState sync() {
        return new AttributeState(this.currentProperties, this.currentProperties);
    }
}
