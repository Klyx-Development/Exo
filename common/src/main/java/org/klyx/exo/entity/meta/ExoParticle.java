package org.klyx.exo.entity.meta;

import org.klyx.exo.entity.meta.particle.ExoParticleData;
import org.klyx.exo.util.Key;

public record ExoParticle(Key key, ExoParticleData data) {
    public static final ExoParticle DUST = new ExoParticle(Key.of("dust"), new ExoParticleData.Dust(0xFF0000, 1.0f));
}
