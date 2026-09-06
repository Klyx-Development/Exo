package org.klyx.exo.entity.meta.particle;

import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.ExoVec3d;
import org.klyx.exo.entity.data.ExoItemStack;
import org.klyx.exo.entity.meta.ExoBlockPos;
import org.klyx.exo.entity.meta.ExoBlockState;

public sealed interface ExoParticleData {

    record None() implements ExoParticleData { }
    record Dust(int color, float scale) implements ExoParticleData { }
    record DustTransition(int fromColor, int toColor, float scale) implements ExoParticleData { }
    record Block(ExoBlockState state) implements ExoParticleData { }
    record Item(ExoItemStack item) implements ExoParticleData { }

    record Vibration(VibrationSource source, int arrivalTicks) implements ExoParticleData {
        public sealed interface VibrationSource permits VibrationSource.FromBlock, VibrationSource.FromEntity {
            record FromBlock(ExoBlockPos pos) implements VibrationSource { }
            record FromEntity(int entityId, float eyeHeight) implements VibrationSource { }
        }
    }

    record Shriek(int delay) implements ExoParticleData { }
    record SculkCharge(float roll) implements ExoParticleData { }
    record Trail(ExoVec3d target, int color, int duration) implements ExoParticleData { }
    record Color(int argb) implements ExoParticleData { }
    record Power(float power) implements ExoParticleData { }
    record Spell(int color, float power) implements ExoParticleData { }
    record Geyser(int waterBlocks, @Nullable Float burstImpulseBase) implements ExoParticleData { }
}
