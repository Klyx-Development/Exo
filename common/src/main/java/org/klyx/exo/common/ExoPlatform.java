package org.klyx.exo.common;

import org.slf4j.Logger;

public interface ExoPlatform {
    Logger logger();

    ViewerVisibilityQuery viewerVisibilityQuery();
    ViewerDispatch viewerDispatch();
    MovementDispatcher movementDispatcher();
    MetadataDispatcher metadataDispatcher();
    AttributeDispatcher attributeDispatcher();
    TickScheduler tickScheduler();
    EquipmentDispatcher equipmentDispatcher();
    LeashDispatcher leashDispatcher();
    PassengerDispatcher passengerDispatcher();
    SpectateDispatcher spectateDispatcher();
}
