package org.klyx.exo.paper.entity.components.types;

import org.bukkit.craftbukkit.CraftEquipmentSlot;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.components.EntityComponent;
import org.klyx.exo.entity.components.EquipmentSlotKey;
import org.klyx.exo.entity.data.ExoItemStack;
import org.klyx.exo.entity.events.EntitySpawnEvent;
import org.klyx.exo.entity.events.ViewerShowEntityEvent;
import org.klyx.exo.paper.dispatch.PaperEquipmentDispatcher;
import org.klyx.exo.paper.meta.PaperItemStacks;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class EquipmentComponent implements EntityComponent {

    private final Map<EquipmentSlotKey, ExoItemStack> equipment = new EnumMap<>(EquipmentSlotKey.class);
    private @Nullable ExoEntity entity;

    @Override
    public void initialize(ExoEntity entity) {
        this.entity = entity;

        entity.eventBus()
                .on(EntitySpawnEvent.class, this::handleSpawn)
                .on(ViewerShowEntityEvent.class, this::handleAddViewer);
    }

    private void handleSpawn(EntitySpawnEvent event) {
        if (this.entity == null || this.equipment.isEmpty()) return;
        for (var viewer : this.entity.getViewerManager().getActiveViewers()) {
            Exo.platform().equipmentDispatcher().dispatchFullSync(this.entity, viewer, this.equipment);
        }
    }

    private void handleAddViewer(ViewerShowEntityEvent event) {
        if (this.entity == null || this.equipment.isEmpty()) return;
        event.addPacketLast(PaperEquipmentDispatcher.createPacket(this.entity.entityId(), this.equipment));
    }

    public EquipmentComponent setEquipment(EquipmentSlotKey slot, ExoItemStack item) {
        this.equipment.put(slot, item);
        sendUpdate(slot, item);
        return this;
    }

    public EquipmentComponent setEquipment(EquipmentSlot slot, ItemStack item) {
        EquipmentSlotKey key = EquipmentSlotKey.valueOf(CraftEquipmentSlot.getNMS(slot).name());
        return setEquipment(key, PaperItemStacks.fromBukkit(item));
    }

    public EquipmentComponent removeEquipment(EquipmentSlotKey slot) {
        this.equipment.remove(slot);
        sendUpdate(slot, ExoItemStack.EMPTY);
        return this;
    }

    public EquipmentComponent removeEquipment(EquipmentSlot slot) {
        return removeEquipment(EquipmentSlotKey.valueOf(CraftEquipmentSlot.getNMS(slot).name()));
    }

    public @Nullable ExoItemStack getEquipment(EquipmentSlotKey slot) {
        return this.equipment.get(slot);
    }

    public Map<EquipmentSlotKey, ExoItemStack> getEquipment() {
        return Collections.unmodifiableMap(this.equipment);
    }

    private void sendUpdate(EquipmentSlotKey slot, ExoItemStack item) {
        if (this.entity == null || !this.entity.isSpawned()) return;
        Exo.platform().equipmentDispatcher().dispatchUpdate(this.entity, slot, item);
    }

}
