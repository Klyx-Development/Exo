package org.klyx.exo.data.attributes;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.bukkit.craftbukkit.CraftRegistry;
import org.bukkit.craftbukkit.attribute.CraftAttribute;
import org.bukkit.craftbukkit.attribute.CraftAttributeMap;

import java.util.ArrayList;
import java.util.List;

public class AttributeEntry extends AttributeInstance {

    private final Holder<Attribute> attributeHolder;
    private final double value;
    private final List<AttributeModifier> modifiers = new ArrayList<>();

    public AttributeEntry(org.bukkit.attribute.Attribute attribute, double value) {
        super(CraftAttribute.bukkitToMinecraftHolder(attribute), instance -> {});
        this.attributeHolder = CraftAttribute.bukkitToMinecraftHolder(attribute);
        this.value = value;
        setBaseValue(value);
    }

    public void addModifier(org.bukkit.attribute.AttributeModifier modifier) {
        addTransientModifier(CraftRegistry.bukkitToMinecraft(modifier));
    }
}
