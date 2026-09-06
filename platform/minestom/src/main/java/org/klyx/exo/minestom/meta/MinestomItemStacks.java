package org.klyx.exo.minestom.meta;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.text.Component;
import net.minestom.server.codec.Result;
import net.minestom.server.codec.Transcoder;
import net.minestom.server.color.Color;
import net.minestom.server.component.DataComponent;
import net.minestom.server.component.DataComponentMap;
import net.minestom.server.component.DataComponents;
import net.minestom.server.entity.EquipmentSlotGroup;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.entity.attribute.AttributeModifier;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.AttributeList;
import net.minestom.server.item.component.CustomModelData;
import net.minestom.server.item.component.EnchantmentList;
import net.minestom.server.item.component.Food;
import net.minestom.server.item.enchant.Enchantment;
import net.minestom.server.registry.RegistryKey;
import net.minestom.server.utils.Unit;
import org.klyx.exo.entity.data.ExoCustomModelData;
import org.klyx.exo.entity.data.ExoFoodProperties;
import org.klyx.exo.entity.data.ExoItemStack;
import org.klyx.exo.entity.data.attribute.ExoAttributeModifier;
import org.klyx.exo.entity.data.attribute.ExoAttributeModifierEntry;
import org.klyx.exo.entity.data.attribute.ExoEquipmentSlotGroup;
import org.klyx.exo.util.Key;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class MinestomItemStacks {

    private static final Set<DataComponent<?>> MODELED_COMPONENTS = Set.of(
            DataComponents.CUSTOM_NAME, DataComponents.LORE, DataComponents.ENCHANTMENTS,
            DataComponents.ATTRIBUTE_MODIFIERS, DataComponents.CUSTOM_MODEL_DATA, DataComponents.DAMAGE,
            DataComponents.MAX_DAMAGE, DataComponents.UNBREAKABLE, DataComponents.FOOD
    );

    public static ItemStack toMinestom(ExoItemStack item) {
        Material material = Material.fromKey(item.material());
        if (material == null) material = Material.AIR;
        ItemStack stack = ItemStack.of(material, item.amount());

        if (item.customName() != null) stack = stack.with(DataComponents.CUSTOM_NAME, item.customName());
        if (!item.lore().isEmpty()) stack = stack.with(DataComponents.LORE, item.lore());

        if (!item.enchantments().isEmpty()) {
            Map<RegistryKey<Enchantment>, Integer> enchantments = new LinkedHashMap<>();
            for (Map.Entry<Key, Integer> entry : item.enchantments().entrySet()) {
                enchantments.put(RegistryKey.unsafeOf(entry.getKey()), entry.getValue());
            }
            stack = stack.with(DataComponents.ENCHANTMENTS, new EnchantmentList(enchantments));
        }

        if (!item.attributeModifiers().isEmpty()) {
            List<AttributeList.Modifier> modifiers = new ArrayList<>(item.attributeModifiers().size());
            for (ExoAttributeModifierEntry entry : item.attributeModifiers()) {
                Attribute attribute = Attribute.fromKey(entry.attribute());
                if (attribute == null) continue;
                modifiers.add(new AttributeList.Modifier(attribute, toMinestomModifier(entry.modifier()), toMinestomSlotGroup(entry.group())));
            }
            stack = stack.with(DataComponents.ATTRIBUTE_MODIFIERS, new AttributeList(modifiers));
        }

        if (item.customModelData() != null) {
            ExoCustomModelData cmd = item.customModelData();
            List<net.kyori.adventure.util.RGBLike> colors = cmd.colors().stream().<net.kyori.adventure.util.RGBLike>map(Color::new).toList();
            stack = stack.with(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(cmd.floats(), cmd.flags(), cmd.strings(), colors));
        }

        if (item.damage() != null) stack = stack.with(DataComponents.DAMAGE, item.damage());
        if (item.maxDamage() != null) stack = stack.with(DataComponents.MAX_DAMAGE, item.maxDamage());
        if (item.unbreakable()) stack = stack.with(DataComponents.UNBREAKABLE, Unit.INSTANCE);

        if (item.food() != null) {
            ExoFoodProperties food = item.food();
            stack = stack.with(DataComponents.FOOD, new Food(food.nutrition(), food.saturation(), food.canAlwaysEat()));
        }

        stack = applyRawComponents(stack, item.rawComponents());
        return stack;
    }

    public static ExoItemStack fromMinestom(ItemStack stack) {
        ExoItemStack.Builder builder = ExoItemStack.builder(toKey(stack.material().key()))
                .amount(stack.amount());

        var customName = stack.get(DataComponents.CUSTOM_NAME);
        if (customName != null) builder.customName(customName);

        List<Component> lore = stack.get(DataComponents.LORE);
        if (lore != null) builder.lore(lore);

        EnchantmentList enchantments = stack.get(DataComponents.ENCHANTMENTS);
        if (enchantments != null) {
            Map<Key, Integer> map = new LinkedHashMap<>();
            enchantments.enchantments().forEach((key, level) -> map.put(toKey(key.key()), level));
            builder.enchantments(map);
        }

        AttributeList attributeModifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
        if (attributeModifiers != null) {
            List<ExoAttributeModifierEntry> entries = new ArrayList<>();
            for (AttributeList.Modifier modifier : attributeModifiers.modifiers()) {
                entries.add(new ExoAttributeModifierEntry(toKey(modifier.attribute().key()),
                        fromMinestomModifier(modifier.modifier()), fromMinestomSlotGroup(modifier.slot())));
            }
            builder.attributeModifiers(entries);
        }

        CustomModelData customModelData = stack.get(DataComponents.CUSTOM_MODEL_DATA);
        if (customModelData != null) {
            List<Integer> colors = customModelData.colors().stream().map(rgb -> new Color(rgb).asRGB()).toList();
            builder.customModelData(new ExoCustomModelData(customModelData.floats(), customModelData.flags(), customModelData.strings(), colors));
        }

        Integer damage = stack.get(DataComponents.DAMAGE);
        if (damage != null) builder.damage(damage);

        Integer maxDamage = stack.get(DataComponents.MAX_DAMAGE);
        if (maxDamage != null) builder.maxDamage(maxDamage);

        builder.unbreakable(stack.has(DataComponents.UNBREAKABLE));

        Food food = stack.get(DataComponents.FOOD);
        if (food != null) builder.food(new ExoFoodProperties(food.nutrition(), food.saturationModifier(), food.canAlwaysEat()));

        readRawComponents(stack, builder);
        return builder.build();
    }

    private static ItemStack applyRawComponents(ItemStack stack, Map<Key, Optional<BinaryTag>> rawComponents) {
        if (rawComponents.isEmpty()) return stack;

        for (Map.Entry<Key, Optional<BinaryTag>> entry : rawComponents.entrySet()) {
            DataComponent<?> type = DataComponent.fromKey(entry.getKey());
            if (type == null) continue;

            if (entry.getValue().isEmpty()) {
                stack = stack.without(type);
                continue;
            }
            stack = setUnchecked(stack, type, entry.getValue().get());
        }
        return stack;
    }

    private static <T> ItemStack setUnchecked(ItemStack stack, DataComponent<T> type, BinaryTag tag) {
        T value = type.decode(Transcoder.NBT, tag).orElseThrow();
        return stack.with(type, value);
    }

    private static void readRawComponents(ItemStack stack, ExoItemStack.Builder builder) {
        DataComponentMap patch = stack.componentPatch();
        if (patch.isEmpty()) return;

        for (DataComponent.Value entry : patch.entrySet()) {
            DataComponent<?> type = entry.component();
            if (MODELED_COMPONENTS.contains(type)) continue;

            Key key = toKey(type.key());
            if (entry.value() == null) {
                builder.removeComponent(key);
                continue;
            }
            encodeUnchecked(type, entry.value()).ifPresent(tag -> builder.rawComponent(key, tag));
        }
    }
    private static <T> Optional<BinaryTag> encodeUnchecked(DataComponent<T> type, Object value) {
        var result = type.encode(Transcoder.NBT, (T) value);
        return result instanceof Result.Ok<BinaryTag>(BinaryTag value1) ? Optional.of(value1) : Optional.empty();
    }

    private static AttributeModifier toMinestomModifier(ExoAttributeModifier modifier) {
        return new AttributeModifier(modifier.key(), modifier.amount(), toMinestomOperation(modifier.operation()));
    }

    private static ExoAttributeModifier fromMinestomModifier(AttributeModifier modifier) {
        return new ExoAttributeModifier(toKey(modifier.id()), modifier.amount(), fromMinestomOperation(modifier.operation()));
    }

    private static net.minestom.server.entity.attribute.AttributeOperation toMinestomOperation(ExoAttributeModifier.Operation operation) {
        return net.minestom.server.entity.attribute.AttributeOperation.valueOf(operation.name());
    }

    private static ExoAttributeModifier.Operation fromMinestomOperation(net.minestom.server.entity.attribute.AttributeOperation operation) {
        return ExoAttributeModifier.Operation.valueOf(operation.name());
    }

    private static EquipmentSlotGroup toMinestomSlotGroup(ExoEquipmentSlotGroup group) {
        return switch (group) {
            case ANY -> EquipmentSlotGroup.ANY;
            case MAIN_HAND -> EquipmentSlotGroup.MAIN_HAND;
            case OFF_HAND -> EquipmentSlotGroup.OFF_HAND;
            case HAND -> EquipmentSlotGroup.HAND;
            case FEET -> EquipmentSlotGroup.FEET;
            case LEGS -> EquipmentSlotGroup.LEGS;
            case CHEST -> EquipmentSlotGroup.CHEST;
            case HEAD -> EquipmentSlotGroup.HEAD;
            case ARMOR -> EquipmentSlotGroup.ARMOR;
            case BODY -> EquipmentSlotGroup.BODY;
            case SADDLE -> EquipmentSlotGroup.SADDLE;
        };
    }

    private static ExoEquipmentSlotGroup fromMinestomSlotGroup(EquipmentSlotGroup group) {
        return switch (group) {
            case ANY -> ExoEquipmentSlotGroup.ANY;
            case MAIN_HAND -> ExoEquipmentSlotGroup.MAIN_HAND;
            case OFF_HAND -> ExoEquipmentSlotGroup.OFF_HAND;
            case HAND -> ExoEquipmentSlotGroup.HAND;
            case FEET -> ExoEquipmentSlotGroup.FEET;
            case LEGS -> ExoEquipmentSlotGroup.LEGS;
            case CHEST -> ExoEquipmentSlotGroup.CHEST;
            case HEAD -> ExoEquipmentSlotGroup.HEAD;
            case ARMOR -> ExoEquipmentSlotGroup.ARMOR;
            case BODY -> ExoEquipmentSlotGroup.BODY;
            case SADDLE -> ExoEquipmentSlotGroup.SADDLE;
        };
    }

    private static Key toKey(net.kyori.adventure.key.Key key) {
        return Key.of(key.namespace(), key.value());
    }
}
