package org.klyx.exo.entity.data;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.data.attribute.ExoAttributeModifierEntry;
import org.klyx.exo.util.Buildable;
import org.klyx.exo.util.Key;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public record ExoItemStack(
        Key material, int amount,
        @Nullable Component customName,
        List<Component> lore,
        Map<Key, Integer> enchantments,
        List<ExoAttributeModifierEntry> attributeModifiers,
        @Nullable ExoCustomModelData customModelData,
        @Nullable Integer damage, @Nullable Integer maxDamage,
        boolean unbreakable,
        @Nullable ExoFoodProperties food,
        Map<Key, Optional<BinaryTag>> rawComponents
) {
    public static final ExoItemStack EMPTY = new ExoItemStack(Key.of("air"), 0, null, List.of(),
            Map.of(), List.of(), null, null, null, false, null, Map.of());

    public static Builder builder(Key material) {
        return new Builder(material);
    }

    public static final class Builder implements Buildable<ExoItemStack> {

        private final Key material;
        private int amount = 1;
        private @Nullable Component customName;
        private List<Component> lore = new ArrayList<>();
        private Map<Key, Integer> enchantments = new LinkedHashMap<>();
        private List<ExoAttributeModifierEntry> attributeModifiers = new ArrayList<>();
        private @Nullable ExoCustomModelData customModelData;
        private @Nullable Integer damage;
        private @Nullable Integer maxDamage;
        private boolean unbreakable;
        private @Nullable ExoFoodProperties food;
        private final Map<Key, Optional<BinaryTag>> rawComponents = new LinkedHashMap<>();

        private Builder(Key material) {
            this.material = material;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder customName(@Nullable Component customName) {
            this.customName = customName;
            return this;
        }

        public Builder lore(List<Component> lore) {
            this.lore = new ArrayList<>(lore);
            return this;
        }

        public Builder addLoreLine(Component line) {
            this.lore.add(line);
            return this;
        }

        public Builder enchant(Key enchantment, int level) {
            this.enchantments.put(enchantment, level);
            return this;
        }

        public Builder enchantments(Map<Key, Integer> enchantments) {
            this.enchantments = new LinkedHashMap<>(enchantments);
            return this;
        }

        public Builder addAttributeModifier(ExoAttributeModifierEntry entry) {
            this.attributeModifiers.add(entry);
            return this;
        }

        public Builder attributeModifiers(List<ExoAttributeModifierEntry> entries) {
            this.attributeModifiers = new ArrayList<>(entries);
            return this;
        }

        public Builder customModelData(@Nullable ExoCustomModelData customModelData) {
            this.customModelData = customModelData;
            return this;
        }

        public Builder damage(@Nullable Integer damage) {
            this.damage = damage;
            return this;
        }

        public Builder maxDamage(@Nullable Integer maxDamage) {
            this.maxDamage = maxDamage;
            return this;
        }

        public Builder unbreakable(boolean unbreakable) {
            this.unbreakable = unbreakable;
            return this;
        }

        public Builder food(@Nullable ExoFoodProperties food) {
            this.food = food;
            return this;
        }

        public Builder rawComponent(Key componentType, BinaryTag value) {
            this.rawComponents.put(componentType, Optional.of(value));
            return this;
        }

        public Builder removeComponent(Key componentType) {
            this.rawComponents.put(componentType, Optional.empty());
            return this;
        }

        @Override
        public ExoItemStack build() {
            return new ExoItemStack(material, amount, customName, List.copyOf(lore), Map.copyOf(enchantments),
                    List.copyOf(attributeModifiers), customModelData, damage, maxDamage, unbreakable, food,
                    Map.copyOf(rawComponents));
        }
    }
}
