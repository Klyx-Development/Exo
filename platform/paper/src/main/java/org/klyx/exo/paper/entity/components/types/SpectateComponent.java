package org.klyx.exo.paper.entity.components.types;

import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.components.EntityComponent;
import org.klyx.exo.entity.events.EntityDespawnEvent;
import org.klyx.exo.paper.player.ExoPaperPlayer;
import org.klyx.exo.player.ExoPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpectateComponent implements EntityComponent {

    private final List<UUID> spectators = new ArrayList<>();
    private @Nullable ExoEntity entity;

    @Override
    public void initialize(ExoEntity entity) {
        this.entity = entity;
        entity.eventBus().on(EntityDespawnEvent.class, this::handleDespawn);
    }

    public void handleDespawn(EntityDespawnEvent event) {
        spectators.forEach(spectator -> unspectate(ExoPaperPlayer.of(spectator)));
    }

    public void spectate(ExoPlayer player) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        Exo.platform().spectateDispatcher().dispatchSpectate(player, entity.entityId());
        spectators.add(player.uuid());
    }

    public void unspectate(ExoPlayer player) {
        Exo.platform().spectateDispatcher().dispatchSpectate(player, player.entityId());
        spectators.remove(player.uuid());
    }

    public List<UUID> getSpectators() {
        return spectators;
    }
}
