package org.klyx.exo.entities.impl.components;

import org.klyx.exo.entities.impl.AbstractEntity;
import org.klyx.exo.storage.EntityStorage;
import org.klyx.exo.utils.PacketUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MountComponent {
    private final AbstractEntity entity;
    private final Set<Integer> passengers = ConcurrentHashMap.newKeySet();
    private int ridingEntityId = -1;

    public MountComponent(AbstractEntity entity) {
        this.entity = entity;
    }

    public void addPassenger(int passengerId) {
        if (passengers.contains(passengerId)) {
            throw new IllegalArgumentException("The passenger with id " + passengerId + " is already riding this entity!");
        }

        passengers.add(passengerId);
        PacketUtil.sendPacket(entity.getViewers(), PacketUtil.createPassengerPacket(entity));

        AbstractEntity passenger = EntityStorage.getEntity(passengerId);
        if (passenger != null && passenger.isAlive()) {
            passenger.getMountComponent().ridingEntityId = entity.getEntityId();
        }
    }

    public void removePassenger(int passengerId) {
        if (!passengers.remove(passengerId)) {
            throw new IllegalArgumentException("The passenger with id " + passengerId + " doesn't exist or is not riding this entity!");
        }

        PacketUtil.sendPacket(entity.getViewers(), PacketUtil.createPassengerPacket(entity));

        AbstractEntity passenger = EntityStorage.getEntity(passengerId);
        if (passenger != null && passenger.isAlive()) {
            passenger.getMountComponent().ridingEntityId = -1;
        }
    }

    public void clearAll() {
        new ArrayList<>(passengers).forEach(this::removePassenger);
        passengers.clear();
        ridingEntityId = -1;
    }

    public List<Integer> getPassengers() {
        return new ArrayList<>(passengers);
    }

    public void setRidingEntityId(int ridingEntityId) {
        this.ridingEntityId = ridingEntityId;
    }

    public int getRidingEntityId() {
        return ridingEntityId;
    }
}
