package org.klyx.exo.entity.components;

import org.jetbrains.annotations.UnmodifiableView;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.event.EventBus;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public final class EntityComponentManager {

    private final EventBus eventBus = new EventBus();
    private final Map<Class<? extends EntityComponent>, EntityComponent> components = new LinkedHashMap<>();

    public EventBus eventBus() {
        return eventBus;
    }

    public <E extends EntityComponent> void addComponent(E component) {
        Objects.requireNonNull(component, "the component cannot be null");
        components.put(component.getClass(), component);
    }

    public <E extends EntityComponent> @Nullable E getComponent(Class<E> type) {
        synchronized (components) {
            EntityComponent extension = components.get(type);
            return extension != null ? type.cast(extension) : null;
        }
    }

    public <E extends EntityComponent> void editComponent(Class<E> type, Consumer<E> editor) {
        synchronized (components) {
            E extension = getComponent(type);
            if (extension != null) {
                editor.accept(extension);
            }
        }
    }

    public boolean hasComponent(Class<? extends EntityComponent> type) {
        synchronized (components) {
            return components.containsKey(type);
        }
    }

    public @UnmodifiableView Collection<EntityComponent> getComponents() {
        synchronized (components) {
            return List.copyOf(components.values());
        }
    }

    public void attachAll(ExoEntity entity) {
        Collection<EntityComponent> snapshot;
        synchronized (components) {
            snapshot = List.copyOf(components.values());
        }
        snapshot.forEach(component -> component.initialize(entity));
    }

    public void detachAll(ExoEntity entity) {
        Collection<EntityComponent> snapshot;
        synchronized (components) {
            snapshot = List.copyOf(components.values());
        }
        snapshot.forEach(component -> component.destroy(entity));
    }

    public void destroy() {
        synchronized (components) {
            components.clear();
        }
    }

}
