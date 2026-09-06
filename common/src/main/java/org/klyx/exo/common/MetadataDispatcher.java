package org.klyx.exo.common;

import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.meta.impl.MetaEntry;

import java.util.List;
import java.util.UUID;

public interface MetadataDispatcher {
    void dispatchFullSync(ExoEntity entity, UUID viewer, List<MetaEntry<?>> entries);
    void dispatchDirty(ExoEntity entity, List<MetaEntry<?>> dirtyEntries);
}
