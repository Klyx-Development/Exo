package org.klyx.exo.paper.entity.components.types;

import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.components.EntityComponent;
import org.klyx.exo.entity.events.EntityDespawnEvent;
import org.klyx.exo.entity.events.EntitySpawnEvent;
import org.klyx.exo.entity.events.ViewerShowEntityEvent;
import org.klyx.exo.paper.dispatch.PaperLeashDispatcher;

public class LeashComponent implements EntityComponent {

    private static final int NO_HOLDER = 0;

    private @Nullable ExoEntity entity;
    private int holder = NO_HOLDER;

    @Override
    public void initialize(ExoEntity entity) {
        this.entity = entity;

        entity.eventBus()
                .on(EntitySpawnEvent.class, this::handleSpawn)
                .on(ViewerShowEntityEvent.class, this::handleAddViewer)
                .on(EntityDespawnEvent.class, this::handleDespawn);
    }

    private void handleSpawn(EntitySpawnEvent event) {
        if (this.entity == null || this.holder == NO_HOLDER) return;
        Exo.platform().leashDispatcher().dispatchLink(this.entity, this.holder);
    }

    private void handleAddViewer(ViewerShowEntityEvent event) {
        if (this.entity == null || this.holder == NO_HOLDER) return;
        event.addPacketLast(PaperLeashDispatcher.createLinkPacket(this.entity.entityId(), this.holder));
    }

    private void handleDespawn(EntityDespawnEvent event) {
        this.holder = NO_HOLDER;
    }

    public LeashComponent leash(int holderEntityId) {
        this.holder = holderEntityId;
        sendUpdate();
        return this;
    }

    public LeashComponent leash(ExoEntity holderEntity) {
        return leash(holderEntity.entityId());
    }

    public LeashComponent leash(org.bukkit.entity.Entity holderEntity) {
        return leash(holderEntity.getEntityId());
    }

    public LeashComponent unleash() {
        this.holder = NO_HOLDER;
        sendUpdate();
        return this;
    }

    public boolean isLeashed() {
        return this.holder != NO_HOLDER;
    }

    public int getHolder() {
        return this.holder;
    }

    private void sendUpdate() {
        if (this.entity == null || !this.entity.isSpawned()) return;
        Exo.platform().leashDispatcher().dispatchLink(this.entity, this.holder);
    }

}
