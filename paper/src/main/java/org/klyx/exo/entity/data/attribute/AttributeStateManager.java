package org.klyx.exo.entity.data.attribute;

import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.klyx.exo.entity.ExoEntity;

import java.util.Collection;
import java.util.List;

public class AttributeStateManager {

    private final ExoEntity entity;
    private AttributeState attributesState;

    public AttributeStateManager(ExoEntity entity, List<ClientboundUpdateAttributesPacket.AttributeSnapshot> initialProperties) {
        this.entity = entity;
        this.attributesState = new AttributeState(initialProperties);
    }

    public AttributeState getAttributesState() {
        return this.attributesState;
    }

    public void setAttribute(Holder<Attribute> attribute, double value, List<AttributeModifier> modifiers) {
        updateState(this.attributesState.with(attribute, value, modifiers));
    }

    public void setAttribute(Holder<Attribute> attribute, double value, AttributeModifier modifier) {
        updateState(this.attributesState.with(attribute, value, modifier));
    }

    public void setAttribute(Holder<Attribute> attribute, double value) {
        updateState(this.attributesState.with(attribute, value));
    }

    public void setAttributes(Iterable<ClientboundUpdateAttributesPacket.AttributeSnapshot> properties) {
        updateState(this.attributesState.withAll(properties));
    }

    public void removeModifier(Holder<Attribute> attribute, AttributeModifier modifier) {
        updateState(this.attributesState.without(attribute, modifier));
    }

    public void removeAttribute(Holder<Attribute> attribute) {
        updateState(this.attributesState.without(attribute));
    }

    public void removeAttributes(Collection<Holder<Attribute>> attributes) {
        updateState(this.attributesState.withoutAll(attributes));
    }

    private void updateState(AttributeState newState) {
        this.attributesState = newState;
        dispatchAttributeUpdates();
    }

    private void dispatchAttributeUpdates() {
        if (!this.entity.isSpawned() || this.entity.getViewerManager().getViewerCount() == 0) {
            markSynced();
            return;
        }
        if (!this.attributesState.hasChanged()) {
            return;
        }
        List<ClientboundUpdateAttributesPacket.AttributeSnapshot> dirtyProperties = this.attributesState.dirtyProperties();
        if (!dirtyProperties.isEmpty()) {
            this.entity.getViewerManager().sentPacketsToViewers(
                    this.attributesState.createPacket(this.entity.entityId())
            );
        }
        markSynced();
    }

    public void markSynced() {
        this.attributesState = this.attributesState.sync();
    }
}