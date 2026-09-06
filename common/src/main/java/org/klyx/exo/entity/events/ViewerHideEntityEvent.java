package org.klyx.exo.entity.events;

import java.util.List;
import java.util.UUID;

public class ViewerHideEntityEvent<P> extends EntityPacketPipelineEvent<P> {

    private final UUID viewer;
    private final boolean isUnload;

    public ViewerHideEntityEvent(List<P> packets, UUID viewer, boolean isUnload) {
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
