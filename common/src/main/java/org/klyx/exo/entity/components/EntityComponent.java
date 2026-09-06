package org.klyx.exo.entity.components;

import org.klyx.exo.entity.ExoEntity;

/**
 * Used to add additional functionality to an entity.
 */
public interface EntityComponent {
    default void initialize(ExoEntity entity) {}
    default void destroy(ExoEntity entity) {}
}
