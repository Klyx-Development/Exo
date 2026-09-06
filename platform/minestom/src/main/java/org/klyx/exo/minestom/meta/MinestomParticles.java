package org.klyx.exo.minestom.meta;

import net.kyori.adventure.util.RGBLike;
import net.minestom.server.color.Color;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.particle.Particle;
import org.klyx.exo.entity.meta.ExoParticle;
import org.klyx.exo.entity.meta.particle.ExoParticleData;

public final class MinestomParticles {

    public static Particle toMinestom(ExoParticle particle) {
        Particle base = Particle.fromKey(particle.key());
        if (base == null) throw new IllegalArgumentException("Unknown particle: " + particle.key().asString());

        return switch (base) {
            case Particle.Dust d -> {
                ExoParticleData.Dust data = (ExoParticleData.Dust) particle.data();
                yield d.withProperties(new Color(data.color()), data.scale());
            }
            case Particle.DustColorTransition d -> {
                ExoParticleData.DustTransition data = (ExoParticleData.DustTransition) particle.data();
                yield d.withProperties(new Color(data.fromColor()), new Color(data.toColor()), data.scale());
            }
            case Particle.Block b -> b.withBlock(MinestomBlockStates.toMinestom(((ExoParticleData.Block) particle.data()).state()));
            case Particle.BlockMarker b -> b.withBlock(MinestomBlockStates.toMinestom(((ExoParticleData.Block) particle.data()).state()));
            case Particle.DustPillar b -> b.withBlock(MinestomBlockStates.toMinestom(((ExoParticleData.Block) particle.data()).state()));
            case Particle.FallingDust b -> b.withBlock(MinestomBlockStates.toMinestom(((ExoParticleData.Block) particle.data()).state()));
            case Particle.BlockCrumble b -> b.withBlock(MinestomBlockStates.toMinestom(((ExoParticleData.Block) particle.data()).state()));
            case Particle.Item i -> i.withItem(MinestomItemStacks.toMinestom(((ExoParticleData.Item) particle.data()).item()));
            case Particle.Vibration v -> {
                ExoParticleData.Vibration data = (ExoParticleData.Vibration) particle.data();
                yield switch (data.source()) {
                    case ExoParticleData.Vibration.VibrationSource.FromBlock fromBlock ->
                            v.withSourceBlockPosition(new Vec(fromBlock.pos().x(), fromBlock.pos().y(), fromBlock.pos().z()), data.arrivalTicks());
                    case ExoParticleData.Vibration.VibrationSource.FromEntity fromEntity ->
                            v.withSourceEntity(fromEntity.entityId(), fromEntity.eyeHeight(), data.arrivalTicks());
                };
            }
            case Particle.Shriek s -> s.withDelay(((ExoParticleData.Shriek) particle.data()).delay());
            case Particle.SculkCharge s -> s.withRoll(((ExoParticleData.SculkCharge) particle.data()).roll());
            case Particle.Trail t -> {
                ExoParticleData.Trail data = (ExoParticleData.Trail) particle.data();
                Point target = new Vec(data.target().x(), data.target().y(), data.target().z());
                yield t.withProperties(target, new Color(data.color()), data.duration());
            }
            case Particle.EntityEffect e -> e.withColor(alpha(((ExoParticleData.Color) particle.data()).argb()), rgb(((ExoParticleData.Color) particle.data()).argb()));
            case Particle.TintedLeaves t -> t.withColor(alpha(((ExoParticleData.Color) particle.data()).argb()), rgb(((ExoParticleData.Color) particle.data()).argb()));
            case Particle.Flash f -> f.withColor(alpha(((ExoParticleData.Color) particle.data()).argb()), rgb(((ExoParticleData.Color) particle.data()).argb()));
            case Particle.DragonBreath d -> d.withPower(((ExoParticleData.Power) particle.data()).power());
            case Particle.Effect e -> {
                ExoParticleData.Spell data = (ExoParticleData.Spell) particle.data();
                yield e.withProperties(new Color(data.color()), data.power());
            }
            case Particle.InstantEffect e -> {
                ExoParticleData.Spell data = (ExoParticleData.Spell) particle.data();
                yield e.withProperties(new Color(data.color()), data.power());
            }
            case Particle.Geyser g -> g.withWaterBlocks(((ExoParticleData.Geyser) particle.data()).waterBlocks());
            case Particle.GeyserPlume g -> g.withWaterBlocks(((ExoParticleData.Geyser) particle.data()).waterBlocks());
            case Particle.GeyserBase g -> {
                ExoParticleData.Geyser data = (ExoParticleData.Geyser) particle.data();
                yield g.withProperties(data.waterBlocks(), data.burstImpulseBase() != null ? data.burstImpulseBase() : 0f);
            }
            case Particle.GeyserPoof g -> {
                ExoParticleData.Geyser data = (ExoParticleData.Geyser) particle.data();
                yield g.withProperties(data.waterBlocks(), data.burstImpulseBase() != null ? data.burstImpulseBase() : 0f);
            }
            case Particle.Simple s -> s;
        };
    }

    private static int alpha(int argb) {
        return (argb >>> 24) & 0xFF;
    }

    private static RGBLike rgb(int argb) {
        return new Color(argb & 0xFFFFFF);
    }
}
