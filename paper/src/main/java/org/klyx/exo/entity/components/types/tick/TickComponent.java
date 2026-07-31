package org.klyx.exo.entity.components.types.tick;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.components.EntityComponent;
import org.klyx.exo.entity.events.EntityTickEvent;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class TickComponent implements EntityComponent {

    private static final Set<ExoEntity> TO_TICK_ENTITIES = ConcurrentHashMap.newKeySet();
    private static @Nullable BukkitTask tickHandler = null;

    private final @Nullable BiConsumer<ExoEntity, EntityTickEvent> onTick;

    public TickComponent() {
        this(null);
    }

    public TickComponent(@Nullable BiConsumer<ExoEntity, EntityTickEvent> onTick) {
        this.onTick = onTick;
    }

    @Override
    public void initialize(ExoEntity entity) {
        if (onTick != null) {
            entity.eventBus().subscribe(EntityTickEvent.class, event -> onTick.accept(entity, event));
        }

        synchronized (TO_TICK_ENTITIES) {
            TO_TICK_ENTITIES.add(entity);
            if (tickHandler != null) return;

            tickHandler = new BukkitRunnable() {
                private long tickCounter = 0L;

                @Override
                public void run() {
                    this.tickCounter++;
                    EntityTickEvent tickEvent = new EntityTickEvent(this.tickCounter);
                    for (ExoEntity toTick : TO_TICK_ENTITIES) {
                        toTick.eventBus().post(tickEvent);
                    }
                }
            }.runTaskTimer(Exo.plugin(), 0L, 1L);
        }
    }

    @Override
    public void destroy(ExoEntity entity) {
        synchronized (TO_TICK_ENTITIES) {
            TO_TICK_ENTITIES.remove(entity);

            if (TO_TICK_ENTITIES.isEmpty() && tickHandler != null) {
                tickHandler.cancel();
                tickHandler = null;
            }
        }
    }
}