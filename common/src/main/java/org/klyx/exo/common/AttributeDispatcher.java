package org.klyx.exo.common;

import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.data.attribute.ExoAttributeSnapshot;

import java.util.List;

public interface AttributeDispatcher {
    void dispatchDirty(ExoEntity entity, List<ExoAttributeSnapshot> dirtySnapshots);
}
