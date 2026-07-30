package org.klyx.exo.entity.meta.impl;

import net.minecraft.network.syncher.EntityDataSerializer;

public record MetaAccessor<T>(int index, EntityDataSerializer<T> serializer, T defaultValue) {
}
