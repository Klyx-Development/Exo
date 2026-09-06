package org.klyx.exo.paper.dispatch;

import com.mojang.datafixers.util.Pair;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.components.EquipmentSlotKey;
import org.klyx.exo.entity.data.ExoItemStack;
import org.klyx.exo.entity.dispatch.PacketCategory;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.paper.meta.PaperItemStacks;
import org.klyx.exo.common.EquipmentDispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class PaperEquipmentDispatcher implements EquipmentDispatcher {

    private final PacketDispatcher<Packet<?>> packetDispatcher;

    public PaperEquipmentDispatcher(PacketDispatcher<Packet<?>> packetDispatcher) {
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public void dispatchFullSync(ExoEntity entity, UUID viewer, Map<EquipmentSlotKey, ExoItemStack> equipment) {
        if (equipment.isEmpty()) return;
        ClientboundSetEquipmentPacket packet = createPacket(entity.entityId(), equipment);
        packetDispatcher.schedule(viewer, PacketCategory.SPAWN, List.of(packet));
    }

    @Override
    public void dispatchUpdate(ExoEntity entity, EquipmentSlotKey slot, ExoItemStack item) {
        ClientboundSetEquipmentPacket packet = new ClientboundSetEquipmentPacket(entity.entityId(), List.of(Pair.of(toNms(slot), toNmsItem(item))));
        for (UUID viewer : entity.getActiveViewers()) {
            packetDispatcher.schedule(viewer, PacketCategory.DEFAULT, List.of(packet));
        }
    }

    public static ClientboundSetEquipmentPacket createPacket(int entityId, Map<EquipmentSlotKey, ExoItemStack> equipment) {
        List<Pair<EquipmentSlot, ItemStack>> slots = new ArrayList<>(equipment.size());
        for (Map.Entry<EquipmentSlotKey, ExoItemStack> entry : equipment.entrySet()) {
            slots.add(Pair.of(toNms(entry.getKey()), toNmsItem(entry.getValue())));
        }
        return new ClientboundSetEquipmentPacket(entityId, slots);
    }

    private static EquipmentSlot toNms(EquipmentSlotKey slot) {
        return EquipmentSlot.valueOf(slot.name());
    }

    private static ItemStack toNmsItem(ExoItemStack item) {
        return PaperItemStacks.toNms(item);
    }
}
