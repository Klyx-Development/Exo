package org.klyx.exo.common;

import org.klyx.exo.entity.ExoEntity;

import java.util.UUID;

public interface ViewerDispatch {
    boolean onViewerAdded(ExoEntity entity, UUID viewer, boolean wasChunkLoad);
    boolean onViewerRemoved(ExoEntity entity, UUID viewer, boolean wasChunkUnload);
}
