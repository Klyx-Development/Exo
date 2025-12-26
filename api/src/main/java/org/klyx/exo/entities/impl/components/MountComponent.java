package org.klyx.exo.entities.impl.components;

import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.world.entity.Entity;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
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

    public void dismount() {
        if (ridingEntityId == -1) return;

        AbstractEntity vehicle = EntityStorage.getEntity(ridingEntityId);
        if (vehicle != null) {
            vehicle.removePassenger(entity);
            return;
        }

        World world = entity.getLocation().getWorld();
        Entity serverEntity = ((CraftWorld) world).getHandle().getEntity(ridingEntityId);
        if (serverEntity != null) {
            ClientboundSetPassengersPacket packet = createServerEntityPassengerPacket(serverEntity, entity.getEntityId(), false);
            PacketUtil.sendPacket(entity.getViewers(), packet);
            ridingEntityId = -1;
        }
    }

    public void mount(AbstractEntity vehicle) {
        if (ridingEntityId != -1) dismount();
        vehicle.addPassenger(entity);
    }

    public void mount(Entity serverEntity) {
        if (ridingEntityId != -1) dismount();

        ridingEntityId = serverEntity.getId();
        ClientboundSetPassengersPacket packet = createServerEntityPassengerPacket(serverEntity, entity.getEntityId(), true);
        PacketUtil.sendPacket(entity.getViewers(), packet);
    }

    public void clearAll() {
        new ArrayList<>(passengers).forEach(this::removePassenger);
        passengers.clear();

        if (ridingEntityId != -1) dismount();
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

    private ClientboundSetPassengersPacket createServerEntityPassengerPacket(
            Entity serverEntity,
            int customPassengerId,
            boolean addPassenger
    ) {
        List<Integer> passengerIds = new ArrayList<>();

        for (Entity passenger : serverEntity.getPassengers()) {
            passengerIds.add(passenger.getId());
        }

        if (addPassenger) {
            passengerIds.add(customPassengerId);
        } else {
            passengerIds.remove(Integer.valueOf(customPassengerId));
        }

        return PacketUtil.createPassengerPacket(serverEntity.getId(), passengerIds);
    }
}
