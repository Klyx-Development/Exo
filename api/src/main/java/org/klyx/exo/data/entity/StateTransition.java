package org.klyx.exo.data.entity;

public record StateTransition(EntityState from, EntityState to, long timestamp) {
}
