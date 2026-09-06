package org.klyx.exo.paper.entity.components.types;

import org.jetbrains.annotations.UnmodifiableView;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.ExoPos;
import org.klyx.exo.entity.components.EntityComponent;
import org.klyx.exo.entity.events.EntityDespawnEvent;
import org.klyx.exo.entity.events.EntitySpawnEvent;
import org.klyx.exo.entity.events.ViewerShowEntityEvent;
import org.klyx.exo.paper.dispatch.PaperPassengerDispatcher;
import org.klyx.exo.world.ExoWorld;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PassengerComponent implements EntityComponent {

    private final Set<Integer> passengers = ConcurrentHashMap.newKeySet();
    private @Nullable ExoEntity entity;
    private @Nullable ExoWorld preRidingWorld;
    private @Nullable ExoPos preRidingPos;
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
            Exo.platform().passengerDispatcher().dispatchPassengers(entity, entity.entityId(), passengers);
        }

        if (this.riding == -1) return;
        Exo.platform().passengerDispatcher().dispatchPassengers(entity, this.riding, List.of(this.entity.entityId()));
    }

    private void handleAddViewer(ViewerShowEntityEvent event) {
        if (this.entity == null) return;

        if (!this.passengers.isEmpty()) {
            event.addPacketLast(PaperPassengerDispatcher.createPassengerPacket(entity.entityId(), passengers));
        }

        if (this.riding == -1) return;
        event.addPacketLast(PaperPassengerDispatcher.createPassengerPacket(this.riding, List.of(this.entity.entityId())));
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
        preRidingWorld = null;
        preRidingPos = null;
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

        this.preRidingWorld = entity.getWorld();
        this.preRidingPos = entity.getLocation();
        this.riding = vehicleId;

        ExoEntity vehicle = Exo.entityManager().getEntity(vehicleId);
        if (vehicle != null && vehicle.hasComponent(PassengerComponent.class)) {
            vehicle.editComponent(PassengerComponent.class, comp -> comp.addPassenger(entity.entityId()));
        } else {
            Exo.platform().passengerDispatcher().dispatchPassengers(entity, vehicleId, List.of(entity.entityId()));
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
            Exo.platform().passengerDispatcher().dispatchPassengers(entity, vehicleId, List.of());
        }

        if (preRidingWorld != null && preRidingPos != null) {
            entity.teleport(preRidingWorld, preRidingPos);
            preRidingWorld = null;
            preRidingPos = null;
        }

        return this;
    }

    public PassengerComponent addPassenger(int passengerId) {
        this.passengers.add(passengerId);
        if (this.entity == null) return this;

        Exo.platform().passengerDispatcher().dispatchPassengers(entity, entity.entityId(), passengers);
        return this;
    }

    public PassengerComponent removePassenger(int passengerId) {
        this.passengers.remove(passengerId);
        if (this.entity == null) return this;

        Exo.platform().passengerDispatcher().dispatchPassengers(entity, entity.entityId(), passengers);
        return this;
    }

    public boolean hasPassenger(int passengerId) {
        return this.passengers.contains(passengerId);
    }

    public @UnmodifiableView Set<Integer> getPassengers() {
        return Collections.unmodifiableSet(this.passengers);
    }

}
