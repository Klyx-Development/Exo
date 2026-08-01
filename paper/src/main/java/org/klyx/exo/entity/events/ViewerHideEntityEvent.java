package org.klyx.exo.entity.events;

import net.minecraft.network.protocol.Packet;

import java.util.List;
import java.util.UUID;

public class ViewerHideEntityEvent extends EntityPacketPipelineEvent {

    private final UUID viewer;
    private final boolean isUnload;

    public ViewerHideEntityEvent(List<Packet<?>> packets, UUID viewer, boolean isUnload) {
        super(packets);
        this.viewer = viewer;
        this.isUnload = isUnload;
    }

    public UUID viewer() {
        return this.viewer;
    }

    public boolean isUnload() {
        return this.isUnload;
    }
}
