package org.klyx.exo.entity.meta.impl;

public record MetaEntry<T>(MetaAccessor<T> accessor, T value) { }
