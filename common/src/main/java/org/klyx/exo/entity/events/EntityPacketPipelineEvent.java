package org.klyx.exo.entity.events;

import org.jetbrains.annotations.UnmodifiableView;
import org.klyx.exo.event.CancellableEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityPacketPipelineEvent<P> extends CancellableEvent {

    private final List<P> packets;

    protected EntityPacketPipelineEvent(List<P> defaultPackets) {
        this.packets = new ArrayList<>(defaultPackets);
    }

    /**
     * @return the packets involved in this pipeline event as an unmodifiable list
     * @see #addPacketFirst(Object)
     * @see #addPacketLast(Object)
     */
    public @UnmodifiableView List<P> packets() {
        return Collections.unmodifiableList(this.packets);
    }

    /**
     * Adds a packet at the beginning of the pipeline.
     * This means it will be sent before all other current packets in the list.
     *
     * @param packet the packet to add to the start of the list
     * @return this event for chaining
     */
    public EntityPacketPipelineEvent<P> addPacketFirst(P packet) {
        this.packets.addFirst(packet);
        return this;
    }

    /**
     * Adds a packet at the end of the pipeline.
     * This means it will be sent after all other current packets in the list.
     *
     * @param packet the packet to add to the end
     * @return this event for chaining
     */
    public EntityPacketPipelineEvent<P> addPacketLast(P packet) {
        this.packets.add(packet);
        return this;
    }
}
