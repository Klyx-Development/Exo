package org.klyx.exo.entity.components.types;

import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.components.EntityComponent;
import org.klyx.exo.entity.events.EntityAttackEvent;

import java.util.function.Consumer;

public class AttackComponent implements EntityComponent {

    private final @Nullable Consumer<EntityAttackEvent> onAttack;

    public AttackComponent(@Nullable Consumer<EntityAttackEvent> onAttack) {
        this.onAttack = onAttack;
    }

    @Override
    public void initialize(ExoEntity entity) {
        if (onAttack != null) entity.eventBus().subscribe(EntityAttackEvent.class, onAttack);
    }
}
