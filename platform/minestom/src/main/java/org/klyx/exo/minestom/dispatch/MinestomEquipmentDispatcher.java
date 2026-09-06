package org.klyx.exo.minestom.dispatch;

import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.play.EntityEquipmentPacket;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.components.EquipmentSlotKey;
import org.klyx.exo.entity.data.ExoItemStack;
import org.klyx.exo.entity.dispatch.PacketCategory;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.minestom.meta.MinestomItemStacks;
import org.klyx.exo.common.EquipmentDispatcher;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public final class MinestomEquipmentDispatcher implements EquipmentDispatcher {

    private final PacketDispatcher<ServerPacket> packetDispatcher;

    public MinestomEquipmentDispatcher(PacketDispatcher<ServerPacket> packetDispatcher) {
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public void dispatchFullSync(ExoEntity entity, UUID viewer, Map<EquipmentSlotKey, ExoItemStack> equipment) {
        if (equipment.isEmpty()) return;
        EntityEquipmentPacket packet = createPacket(entity.entityId(), equipment);
        packetDispatcher.schedule(viewer, PacketCategory.SPAWN, java.util.List.of(packet));
    }

    @Override
    public void dispatchUpdate(ExoEntity entity, EquipmentSlotKey slot, ExoItemStack item) {
        EntityEquipmentPacket packet = new EntityEquipmentPacket(entity.entityId(), Map.of(toMinestom(slot), toMinestomItem(item)));
        for (UUID viewer : entity.getActiveViewers()) {
            packetDispatcher.schedule(viewer, PacketCategory.DEFAULT, java.util.List.of(packet));
        }
    }

    public static EntityEquipmentPacket createPacket(int entityId, Map<EquipmentSlotKey, ExoItemStack> equipment) {
        Map<EquipmentSlot, ItemStack> slots = new EnumMap<>(EquipmentSlot.class);
        for (Map.Entry<EquipmentSlotKey, ExoItemStack> entry : equipment.entrySet()) {
            slots.put(toMinestom(entry.getKey()), toMinestomItem(entry.getValue()));
        }
        return new EntityEquipmentPacket(entityId, slots);
    }

    private static EquipmentSlot toMinestom(EquipmentSlotKey slot) {
        return switch (slot) {
            case MAINHAND -> EquipmentSlot.MAIN_HAND;
            case OFFHAND -> EquipmentSlot.OFF_HAND;
            case FEET -> EquipmentSlot.BOOTS;
            case LEGS -> EquipmentSlot.LEGGINGS;
            case CHEST -> EquipmentSlot.CHESTPLATE;
            case HEAD -> EquipmentSlot.HELMET;
            case BODY -> EquipmentSlot.BODY;
            case SADDLE -> EquipmentSlot.SADDLE;
        };
    }

    private static ItemStack toMinestomItem(ExoItemStack item) {
        return MinestomItemStacks.toMinestom(item);
    }
}
