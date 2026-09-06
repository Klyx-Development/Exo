package org.klyx.exo.common;

import org.klyx.exo.entity.meta.impl.MetaType;

public interface MetaSerializerRegistry<S> {
    S serializerFor(MetaType type);
}
