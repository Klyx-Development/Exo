package org.klyx.exo.common;

import org.klyx.exo.entity.ExoEntity;

import java.util.Collection;

public interface PassengerDispatcher {
    void dispatchPassengers(ExoEntity viewerSource, int vehicleEntityId, Collection<Integer> passengerIds);
}
