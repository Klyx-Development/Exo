package org.klyx.exo.entity.components.types;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.components.EntityComponent;
import org.klyx.exo.entity.events.EntityDespawnEvent;
import org.klyx.exo.entity.events.EntitySpawnEvent;
import org.klyx.exo.entity.events.ViewerShowEntityEvent;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PassengerComponent implements EntityComponent {

    private final Set<Integer> passengers = ConcurrentHashMap.newKeySet();
    private @Nullable ExoEntity entity;
    private @Nullable Location preRidingLocation;
    private int riding = -1;

    @Override
    public void initialize(ExoEntity entity) {
        this.entity = entity;

        entity.eventBus()
                .on(EntitySpawnEvent.class, this::handleSpawn)
                .on(ViewerShowEntityEvent.class, this::handleAddViewer)
                .on(EntityDespawnEvent.class, this::handleDespawn);
    }

    private void handleSpawn(EntitySpawnEvent event) {
        if (this.entity == null) return;

        if (!this.passengers.isEmpty()) {
            ClientboundSetPassengersPacket packet = this.createPassengerPacket(entity.entityId(), passengers);
            if (packet != null) {
                entity.sendPacketsToViewers(packet);
            }
        }

        if (this.riding == -1) return;
        this.entity.sendPacketsToViewers(createPassengerPacket(this.riding, List.of(this.entity.entityId())));
    }

    private void handleAddViewer(ViewerShowEntityEvent event) {
        if (this.entity == null) return;

        if (!this.passengers.isEmpty()) {
            ClientboundSetPassengersPacket packet = this.createPassengerPacket(entity.entityId(), passengers);
            if (packet == null) return;

            event.addPacketLast(packet);
        }

        if (this.riding == -1) return;
        event.addPacketLast(createPassengerPacket(this.riding, List.of(this.entity.entityId())));
    }

    private void handleDespawn(EntityDespawnEvent event) {
        stopRiding();
        for (int passengerId : passengers) {
            ExoEntity passenger = Exo.entityManager().getEntity(passengerId);
            if (passenger != null) {
                passenger.editComponent(PassengerComponent.class, PassengerComponent::stopRiding);
            }
        }

        passengers.clear();
        preRidingLocation = null;
        riding = -1;
    }

    public int getRiding() {
        return riding;
    }

    public boolean isRiding() {
        return riding != -1;
    }

    public PassengerComponent startRiding(int vehicleId) {
        if (this.entity == null) return this;
        if (this.riding != -1) {
            stopRiding();
        }

        this.preRidingLocation = entity.getLocation();
        this.riding = vehicleId;

        ExoEntity vehicle = Exo.entityManager().getEntity(vehicleId);
        if (vehicle != null && vehicle.hasComponent(PassengerComponent.class)) {
            vehicle.editComponent(PassengerComponent.class, comp -> comp.addPassenger(entity.entityId()));
        } else {
            entity.sendPacketsToViewers(createPassengerPacket(vehicleId, List.of(entity.entityId())));
        }
        return this;
    }

    public PassengerComponent stopRiding() {
        if (this.entity == null) return this;
        if (this.riding == -1) return this;

        int vehicleId = this.riding;
        this.riding = -1;

        ExoEntity vehicle = Exo.entityManager().getEntity(vehicleId);
        if (vehicle != null && vehicle.hasComponent(PassengerComponent.class)) {
            vehicle.editComponent(PassengerComponent.class, comp -> comp.removePassenger(entity.entityId()));
        } else {
            entity.sendPacketsToViewers(createPassengerPacket(vehicleId, List.of()));
        }

        if (preRidingLocation != null) {
            entity.teleport(preRidingLocation);
            preRidingLocation = null;
        }

        return this;
    }

    public PassengerComponent addPassenger(int passengerId) {
        this.passengers.add(passengerId);
        if (this.entity == null) return this;

        ClientboundSetPassengersPacket packet = createPassengerPacket(entity.entityId(), passengers);
        if (packet != null) {
            this.entity.sendPacketsToViewers(packet);
        }
        return this;
    }

    public PassengerComponent removePassenger(int passengerId) {
        this.passengers.remove(passengerId);
        if (this.entity == null) return this;

        ClientboundSetPassengersPacket packet = createPassengerPacket(entity.entityId(), passengers);
        if (packet != null) {
            this.entity.sendPacketsToViewers(packet);
        }
        return this;
    }

    public boolean hasPassenger(int passengerId) {
        return this.passengers.contains(passengerId);
    }

    public @UnmodifiableView  Set<Integer> getPassengers() {
        return Collections.unmodifiableSet(this.passengers);
    }

    private @NotNull ClientboundSetPassengersPacket createPassengerPacket(int entityId, Collection<Integer> passengers) {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        try {
            buffer.writeVarInt(entityId);

            int[] passengerArray = passengers.stream().mapToInt(Integer::intValue).toArray();
            buffer.writeVarIntArray(passengerArray);

            Constructor<ClientboundSetPassengersPacket> packet = ClientboundSetPassengersPacket.class.getDeclaredConstructor(FriendlyByteBuf.class);
            packet.setAccessible(true);

            return packet.newInstance(buffer);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while creating a passenger packet: " + e.getMessage());
        } finally {
            buffer.release();
        }
    }
}
