package org.klyx.exo.entity.events;

import java.util.List;
import java.util.UUID;

public class ViewerShowEntityEvent<P> extends EntityPacketPipelineEvent<P> {

    private final UUID viewer;
    private final boolean isLoad;

    public ViewerShowEntityEvent(List<P> packets, UUID viewer, boolean isLoad) {
        super(packets);
        this.viewer = viewer;
        this.isLoad = isLoad;
    }

    public UUID viewer() {
        return this.viewer;
    }

    public boolean isLoad() {
        return this.isLoad;
    }

}
