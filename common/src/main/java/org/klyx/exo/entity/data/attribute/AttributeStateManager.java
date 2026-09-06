package org.klyx.exo.entity.data.attribute;

import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;

import java.util.Collection;
import java.util.List;

public class AttributeStateManager {

    private final ExoEntity entity;
    private AttributeState attributesState;

    public AttributeStateManager(ExoEntity entity, List<ExoAttributeSnapshot> initialProperties) {
        this.entity = entity;
        this.attributesState = new AttributeState(initialProperties);
    }

    public AttributeState getAttributesState() {
        return this.attributesState;
    }

    public void setAttribute(ExoAttribute attribute, double value, List<ExoAttributeModifier> modifiers) {
        updateState(this.attributesState.with(attribute, value, modifiers));
    }

    public void setAttribute(ExoAttribute attribute, double value, ExoAttributeModifier modifier) {
        updateState(this.attributesState.with(attribute, value, modifier));
    }

    public void setAttribute(ExoAttribute attribute, double value) {
        updateState(this.attributesState.with(attribute, value));
    }

    public void setAttributes(Iterable<ExoAttributeSnapshot> properties) {
        updateState(this.attributesState.withAll(properties));
    }

    public void removeModifier(ExoAttribute attribute, ExoAttributeModifier modifier) {
        updateState(this.attributesState.without(attribute, modifier));
    }

    public void removeAttribute(ExoAttribute attribute) {
        updateState(this.attributesState.without(attribute));
    }

    public void removeAttributes(Collection<ExoAttribute> attributes) {
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
        List<ExoAttributeSnapshot> dirtyProperties = this.attributesState.dirtyProperties();
        if (!dirtyProperties.isEmpty()) {
            Exo.platform().attributeDispatcher().dispatchDirty(this.entity, dirtyProperties);
        }
        markSynced();
    }

    public void markSynced() {
        this.attributesState = this.attributesState.sync();
    }
}
