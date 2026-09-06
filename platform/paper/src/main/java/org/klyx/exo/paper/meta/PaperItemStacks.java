package org.klyx.exo.paper.meta;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.nbt.BinaryTag;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.data.ExoCustomModelData;
import org.klyx.exo.entity.data.ExoFoodProperties;
import org.klyx.exo.entity.data.ExoItemStack;
import org.klyx.exo.entity.data.attribute.ExoAttributeModifier;
import org.klyx.exo.entity.data.attribute.ExoAttributeModifierEntry;
import org.klyx.exo.entity.data.attribute.ExoEquipmentSlotGroup;
import org.klyx.exo.paper.util.PaperNbt;
import org.klyx.exo.paper.util.PaperRegistryAccess;
import org.klyx.exo.util.Key;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class PaperItemStacks {

    private static final Set<DataComponentType<?>> MODELED_COMPONENTS = Set.of(
            DataComponents.CUSTOM_NAME, DataComponents.LORE, DataComponents.ENCHANTMENTS,
            DataComponents.ATTRIBUTE_MODIFIERS, DataComponents.CUSTOM_MODEL_DATA, DataComponents.DAMAGE,
            DataComponents.MAX_DAMAGE, DataComponents.UNBREAKABLE, DataComponents.FOOD
    );

    public static ItemStack toNms(ExoItemStack item) {
        Holder<Item> itemHolder = resolveItem(item.material());
        ItemStack stack = new ItemStack(itemHolder, item.amount());

        if (item.customName() != null) stack.set(DataComponents.CUSTOM_NAME, PaperAdventure.asVanilla(item.customName()));
        if (!item.lore().isEmpty()) stack.set(DataComponents.LORE, new ItemLore(item.lore().stream().map(PaperAdventure::asVanilla).toList()));

        if (!item.enchantments().isEmpty()) {
            ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
            for (Map.Entry<Key, Integer> entry : item.enchantments().entrySet()) {
                Holder<Enchantment> enchantment = resolveEnchantment(entry.getKey());
                if (enchantment != null) mutable.set(enchantment, entry.getValue());
            }
            stack.set(DataComponents.ENCHANTMENTS, mutable.toImmutable());
        }

        if (!item.attributeModifiers().isEmpty()) {
            List<ItemAttributeModifiers.Entry> entries = new ArrayList<>(item.attributeModifiers().size());
            for (ExoAttributeModifierEntry entry : item.attributeModifiers()) {
                Holder<Attribute> attribute = resolveAttribute(entry.attribute());
                if (attribute == null) continue;
                entries.add(new ItemAttributeModifiers.Entry(attribute, toNmsModifier(entry.modifier()), toNmsSlotGroup(entry.group())));
            }
            stack.set(DataComponents.ATTRIBUTE_MODIFIERS, new ItemAttributeModifiers(entries));
        }

        if (item.customModelData() != null) {
            ExoCustomModelData cmd = item.customModelData();
            stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(cmd.floats(), cmd.flags(), cmd.strings(), cmd.colors()));
        }

        if (item.damage() != null) stack.set(DataComponents.DAMAGE, item.damage());
        if (item.maxDamage() != null) stack.set(DataComponents.MAX_DAMAGE, item.maxDamage());
        if (item.unbreakable()) stack.set(DataComponents.UNBREAKABLE, Unit.INSTANCE);

        if (item.food() != null) {
            ExoFoodProperties food = item.food();
            stack.set(DataComponents.FOOD, new FoodProperties(food.nutrition(), food.saturation(), food.canAlwaysEat()));
        }

        applyRawComponents(stack, item.rawComponents());
        return stack;
    }

    public static ExoItemStack fromNms(ItemStack stack) {
        ExoItemStack.Builder builder = ExoItemStack.builder(toKey(BuiltInRegistries.ITEM.getKey(stack.getItem())))
                .amount(stack.getCount());

        var customName = stack.get(DataComponents.CUSTOM_NAME);
        if (customName != null) builder.customName(PaperAdventure.asAdventure(customName));

        ItemLore lore = stack.get(DataComponents.LORE);
        if (lore != null) builder.lore(lore.lines().stream().map(PaperAdventure::asAdventure).toList());

        ItemEnchantments enchantments = stack.get(DataComponents.ENCHANTMENTS);
        if (enchantments != null) {
            Map<Key, Integer> map = new LinkedHashMap<>();
            enchantments.keySet().forEach(holder -> map.put(toKey(holder.unwrapKey().orElseThrow().identifier()), enchantments.getLevel(holder)));
            builder.enchantments(map);
        }

        ItemAttributeModifiers attributeModifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
        if (attributeModifiers != null) {
            List<ExoAttributeModifierEntry> entries = new ArrayList<>();
            for (ItemAttributeModifiers.Entry entry : attributeModifiers.modifiers()) {
                entries.add(new ExoAttributeModifierEntry(
                        toKey(entry.attribute().unwrapKey().orElseThrow().identifier()),
                        fromNmsModifier(entry.modifier()),
                        fromNmsSlotGroup(entry.slot())));
            }

            builder.attributeModifiers(entries);
        }

        CustomModelData customModelData = stack.get(DataComponents.CUSTOM_MODEL_DATA);
        if (customModelData != null) {
            builder.customModelData(new ExoCustomModelData(customModelData.floats(), customModelData.flags(),
                    customModelData.strings(), customModelData.colors()));
        }

        Integer damage = stack.get(DataComponents.DAMAGE);
        if (damage != null) builder.damage(damage);

        Integer maxDamage = stack.get(DataComponents.MAX_DAMAGE);
        if (maxDamage != null) builder.maxDamage(maxDamage);
        builder.unbreakable(stack.has(DataComponents.UNBREAKABLE));

        FoodProperties food = stack.get(DataComponents.FOOD);
        if (food != null) builder.food(new ExoFoodProperties(food.nutrition(), food.saturation(), food.canAlwaysEat()));

        readRawComponents(stack, builder);
        return builder.build();
    }

    public static ExoItemStack fromBukkit(org.bukkit.inventory.ItemStack bukkitItem) {
        return fromNms(CraftItemStack.asNMSCopy(bukkitItem));
    }

    private static void applyRawComponents(ItemStack stack, Map<Key, Optional<BinaryTag>> rawComponents) {
        if (rawComponents.isEmpty()) return;

        RegistryOps<Tag> ops = PaperRegistryAccess.get().createSerializationContext(NbtOps.INSTANCE);
        for (Map.Entry<Key, Optional<BinaryTag>> entry : rawComponents.entrySet()) {
            DataComponentType<?> type = resolveComponentType(entry.getKey());
            if (type == null) continue;

            if (entry.getValue().isEmpty()) {
                stack.remove(type);
                continue;
            }

            Codec<?> codec = type.codec();
            if (codec == null) continue;

            Tag tag = PaperNbt.toNmsTag(entry.getValue().get());
            DataResult<?> result = codec.parse(ops, tag);
            result.resultOrPartial(_ -> {}).ifPresent(value -> setUnchecked(stack, type, value));
        }
    }

    private static <T> void setUnchecked(ItemStack stack, DataComponentType<T> type, Object value) {
        stack.set(type, (T) value);
    }

    private static void readRawComponents(ItemStack stack, ExoItemStack.Builder builder) {
        DataComponentPatch patch = stack.getComponentsPatch();
        if (patch.isEmpty()) return;

        RegistryOps<Tag> ops = PaperRegistryAccess.get().createSerializationContext(NbtOps.INSTANCE);
        for (Map.Entry<DataComponentType<?>, Optional<?>> entry : patch.entrySet()) {
            DataComponentType<?> type = entry.getKey();
            if (MODELED_COMPONENTS.contains(type)) continue;

            Key key = toKey(BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(type));
            if (entry.getValue().isEmpty()) {
                builder.removeComponent(key);
                continue;
            }
            encodeUnchecked(ops, type, entry.getValue().get()).ifPresent(tag -> builder.rawComponent(key, PaperNbt.toBinaryTag(tag)));
        }
    }

    private static <T> Optional<Tag> encodeUnchecked(RegistryOps<Tag> ops, DataComponentType<T> type, Object value) {
        Codec<T> codec = type.codec();
        if (codec == null) return Optional.empty();
        return codec.encodeStart(ops, (T) value).resultOrPartial(_ -> {});
    }

    private static Holder<Item> resolveItem(Key material) {
        Identifier id = Identifier.fromNamespaceAndPath(material.namespace(), material.value());
        return BuiltInRegistries.ITEM.get(id).map(h -> (Holder<Item>) h).orElseGet(() -> BuiltInRegistries.ITEM.wrapAsHolder(Items.AIR));
    }

    private static @Nullable DataComponentType<?> resolveComponentType(Key key) {
        Identifier id = Identifier.fromNamespaceAndPath(key.namespace(), key.value());
        return BuiltInRegistries.DATA_COMPONENT_TYPE.get(id).map(Holder::value).orElse(null);
    }

    private static @Nullable Holder<Attribute> resolveAttribute(Key key) {
        Identifier id = Identifier.fromNamespaceAndPath(key.namespace(), key.value());
        return BuiltInRegistries.ATTRIBUTE.get(id).orElse(null);
    }

    private static @Nullable Holder<Enchantment> resolveEnchantment(Key key) {
        Identifier id = Identifier.fromNamespaceAndPath(key.namespace(), key.value());
        return PaperRegistryAccess.get().lookupOrThrow(Registries.ENCHANTMENT).get(id).orElse(null);
    }

    private static AttributeModifier toNmsModifier(ExoAttributeModifier modifier) {
        Identifier id = Identifier.fromNamespaceAndPath(modifier.key().namespace(), modifier.key().value());
        return new AttributeModifier(id, modifier.amount(), AttributeModifier.Operation.valueOf(modifier.operation().name()));
    }

    private static ExoAttributeModifier fromNmsModifier(AttributeModifier modifier) {
        return new ExoAttributeModifier(toKey(modifier.id()), modifier.amount(),
                ExoAttributeModifier.Operation.valueOf(modifier.operation().name()));
    }

    private static EquipmentSlotGroup toNmsSlotGroup(ExoEquipmentSlotGroup group) {
        return switch (group) {
            case ANY -> EquipmentSlotGroup.ANY;
            case MAIN_HAND -> EquipmentSlotGroup.MAINHAND;
            case OFF_HAND -> EquipmentSlotGroup.OFFHAND;
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

    private static ExoEquipmentSlotGroup fromNmsSlotGroup(EquipmentSlotGroup group) {
        return switch (group) {
            case ANY -> ExoEquipmentSlotGroup.ANY;
            case MAINHAND -> ExoEquipmentSlotGroup.MAIN_HAND;
            case OFFHAND -> ExoEquipmentSlotGroup.OFF_HAND;
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

    private static Key toKey(Identifier id) {
        return Key.of(id.getNamespace(), id.getPath());
    }
}
