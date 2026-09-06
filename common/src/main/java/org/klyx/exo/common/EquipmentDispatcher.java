package org.klyx.exo.common;

import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.components.EquipmentSlotKey;
import org.klyx.exo.entity.data.ExoItemStack;

import java.util.Map;
import java.util.UUID;

public interface EquipmentDispatcher {
    void dispatchFullSync(ExoEntity entity, UUID viewer, Map<EquipmentSlotKey, ExoItemStack> equipment);
    void dispatchUpdate(ExoEntity entity, EquipmentSlotKey slot, ExoItemStack item);
}
