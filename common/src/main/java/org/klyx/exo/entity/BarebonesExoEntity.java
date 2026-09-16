package org.klyx.exo.entity;

/**
 * The purpose of this type of Exo entity is when you want to use the same Exo entity you already have,
 * but you don't want any tracking, or anything like that - you just want to spawn the entity
 * and forget about it. Useful for large quantities of entities spawned at once so Exo doesn't
 * have to tick all of those entities at once.
 */
public abstract class BarebonesExoEntity extends ExoEntity {

    protected BarebonesExoEntity() {
        super(false);
    }
}
