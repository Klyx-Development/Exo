package org.klyx.exo.data.equipment;

import com.mojang.datafixers.util.Pair;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.world.entity.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.klyx.exo.entities.impl.AbstractEntity;
import org.klyx.exo.utils.PacketUtil;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class EntityEquipment {

    private final AbstractEntity entity;
    private final Map<EquipmentSlot, ItemStack> equipment = new EnumMap<>(EquipmentSlot.class);

    public EntityEquipment(AbstractEntity entity) {
        this.entity = entity;
    }

    public void setItem(@NotNull EquipmentSlot slot, @Nullable ItemStack item) {
        if (item == null) {
            equipment.remove(slot);
        } else {
            equipment.put(slot, item);
        }

        refresh();
    }

    public @NotNull ItemStack getItem(@NotNull EquipmentSlot slot) {
        ItemStack item = equipment.get(slot);
        return item == null ? ItemStack.empty() : item;
    }

    public @NotNull ItemStack getHelmet() {
        return getItem(EquipmentSlot.HEAD);
    }

    public @NotNull ItemStack getChestplate() {
        return getItem(EquipmentSlot.CHEST);
    }

    public @NotNull ItemStack getLeggings() {
        return getItem(EquipmentSlot.LEGS);
    }

    public @NotNull ItemStack getBoots() {
        return getItem(EquipmentSlot.FEET);
    }

    public @NotNull ItemStack getMainHand() {
        return getItem(EquipmentSlot.MAINHAND);
    }

    public @NotNull ItemStack getOffhand() {
        return getItem(EquipmentSlot.OFFHAND);
    }

    public void cleanup() {
        equipment.clear();
        refresh();
    }

    public void refresh() {
        ClientboundSetEquipmentPacket packet = createPacket();
        if (packet == null) return;
        PacketUtil.sendPacket(entity.getViewers(), packet);
    }

    public @Nullable ClientboundSetEquipmentPacket createPacket() {
        List<Pair<EquipmentSlot, net.minecraft.world.item.ItemStack>> equipment = new ArrayList<>();
        for (Map.Entry<EquipmentSlot, ItemStack> items : this.equipment.entrySet()) {
            equipment.add(Pair.of(items.getKey(), CraftItemStack.asNMSCopy(items.getValue())));
        }

        if (equipment.isEmpty()) return null;
        return new ClientboundSetEquipmentPacket(entity.getEntityId(), equipment);
    }
}
