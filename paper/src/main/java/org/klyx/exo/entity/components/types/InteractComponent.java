package org.klyx.exo.entity.components.types;

import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.components.EntityComponent;
import org.klyx.exo.entity.events.EntityInteractEvent;

import java.util.function.Consumer;

public class InteractComponent implements EntityComponent {

    private final @Nullable Consumer<EntityInteractEvent> onInteract;

    public InteractComponent(@Nullable Consumer<EntityInteractEvent> onInteract) {
        this.onInteract = onInteract;
    }

    @Override
    public void initialize(ExoEntity entity) {
        if (onInteract != null) entity.eventBus().subscribe(EntityInteractEvent.class, onInteract);
    }

}
