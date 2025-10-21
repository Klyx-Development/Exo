package org.klyx.exo.data.attributes;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AttributeEntry extends AttributeInstance {

    private final Holder<Attribute> attributeHolder;
    private final double value;
    private final List<AttributeModifier> modifiers = new ArrayList<>();

    public AttributeEntry(Attribute attribute, double value) {
        super(BuiltInRegistries.ATTRIBUTE.wrapAsHolder(attribute), instance -> {});
        this.attributeHolder = BuiltInRegistries.ATTRIBUTE.wrapAsHolder(attribute);
        this.value = value;
        setBaseValue(value);
    }

    public void addModifier(AttributeModifier modifier) {
        addTransientModifier(modifier);
    }
}
