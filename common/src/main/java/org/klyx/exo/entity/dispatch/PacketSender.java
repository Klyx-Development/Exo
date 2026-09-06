package org.klyx.exo.entity.dispatch;

import java.util.List;
import java.util.UUID;

public interface PacketSender<P> {
    void sendBatch(UUID viewerUUID, List<P> orderedPackets);

    default void onFlushError(UUID viewerUUID, Exception e) { }
}
