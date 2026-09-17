package org.klyx.exo.entity;

import org.klyx.exo.Exo;
import org.klyx.exo.data.EntityId;
import org.klyx.exo.entity.data.ExoEntityType;
import org.klyx.exo.entity.meta.impl.AbstractEntityMeta;

import java.util.Collection;
import java.util.UUID;

/**
 * The purpose of this type of Exo entity is just to be very, very lightweight.
 * Not tracked at all by Exo, and it's main purpose is to be used for large groups of entities
 * at once, since creating hundreds of {@link ExoEntity} classes can be very taxing considering everything
 * attached to one.
 */
public abstract class LightweightExoEntity {

    private final int entityId = EntityId.next();
    private final UUID uuid = UUID.randomUUID();

    public abstract ExoEntityType entityType();

    public abstract AbstractEntityMeta meta();

    public final int entityId() {
        return entityId;
    }

    public final UUID uuid() {
        return uuid;
    }

    public final void spawn(ExoPos pos, Collection<UUID> viewers) {
        Exo.platform().lightweightEntityDispatcher().spawn(this, pos, viewers);
    }

    /** Sends only the meta fields changed since the last {@link #spawn} or {@link #updateMeta} call. */
    public final void updateMeta(Collection<UUID> viewers) {
        Exo.platform().lightweightEntityDispatcher().updateMeta(this, viewers);
    }

    public final void despawn(Collection<UUID> viewers) {
        Exo.platform().lightweightEntityDispatcher().despawn(this, viewers);
    }
}
