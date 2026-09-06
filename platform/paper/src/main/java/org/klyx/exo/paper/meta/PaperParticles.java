package org.klyx.exo.paper.meta;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.GeyserBaseParticleOptions;
import net.minecraft.core.particles.GeyserParticleOptions;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.PowerParticleOption;
import net.minecraft.core.particles.SculkChargeParticleOptions;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.core.particles.SpellParticleOption;
import net.minecraft.core.particles.TrailParticleOption;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;
import org.klyx.exo.entity.meta.ExoParticle;
import org.klyx.exo.entity.meta.particle.ExoParticleData;
import org.klyx.exo.paper.util.PaperLocUtil;
import org.klyx.exo.util.Key;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class PaperParticles {

    private static final Constructor<EntityPositionSource> ENTITY_POSITION_SOURCE_CONSTRUCTOR;

    static {
        try {
            ENTITY_POSITION_SOURCE_CONSTRUCTOR = EntityPositionSource.class.getDeclaredConstructor(Either.class, float.class);
            ENTITY_POSITION_SOURCE_CONSTRUCTOR.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static ParticleOptions toNms(ExoParticle particle) {
        return switch (particle.data()) {
            case ExoParticleData.None ignored -> (ParticleOptions) resolveType(particle.key());
            case ExoParticleData.Dust d -> new DustParticleOptions(d.color(), d.scale());
            case ExoParticleData.DustTransition d -> new DustColorTransitionOptions(d.fromColor(), d.toColor(), d.scale());
            case ExoParticleData.Block b -> new BlockParticleOption(
                    resolveType(particle.key()),
                    PaperBlockStates.toNms(b.state()));
            case ExoParticleData.Item i -> new ItemParticleOption(
                    resolveType(particle.key()),
                    toItemStackTemplate(i.item()));
            case ExoParticleData.Vibration v -> new VibrationParticleOption(toPositionSource(v.source()), v.arrivalTicks());
            case ExoParticleData.Shriek s -> new ShriekParticleOption(s.delay());
            case ExoParticleData.SculkCharge s -> new SculkChargeParticleOptions(s.roll());
            case ExoParticleData.Trail t -> new TrailParticleOption(
                    new Vec3(t.target().x(), t.target().y(), t.target().z()), t.color(), t.duration());
            case ExoParticleData.Color c -> ColorParticleOption.create(
                    resolveType(particle.key()), c.argb());
            case ExoParticleData.Power p -> PowerParticleOption.create(
                    resolveType(particle.key()), p.power());
            case ExoParticleData.Spell s -> SpellParticleOption.create(
                    resolveType(particle.key()), s.color(), s.power());
            case ExoParticleData.Geyser g -> g.burstImpulseBase() != null
                    ? new GeyserBaseParticleOptions(resolveType(particle.key()), g.waterBlocks(), g.burstImpulseBase())
                    : new GeyserParticleOptions(resolveType(particle.key()), g.waterBlocks());
        };
    }

    private static ItemStackTemplate toItemStackTemplate(org.klyx.exo.entity.data.ExoItemStack item) {
        net.minecraft.world.item.ItemStack stack = PaperItemStacks.toNms(item);
        return new ItemStackTemplate(stack.typeHolder(), stack.getCount(), stack.getComponentsPatch());
    }

    private static PositionSource toPositionSource(ExoParticleData.Vibration.VibrationSource source) {
        return switch (source) {
            case ExoParticleData.Vibration.VibrationSource.FromBlock fromBlock ->
                    new BlockPositionSource(PaperLocUtil.toBlockPos(fromBlock.pos()));
            case ExoParticleData.Vibration.VibrationSource.FromEntity fromEntity ->
                    entityPositionSource(fromEntity.entityId(), fromEntity.eyeHeight());
        };
    }

    private static EntityPositionSource entityPositionSource(int entityId, float eyeHeight) {
        try {
            return ENTITY_POSITION_SOURCE_CONSTRUCTOR.newInstance(Either.right(Either.right(entityId)), eyeHeight);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to init EntityPositionSource: " + e.getMessage(), e);
        }
    }

    private static <T extends ParticleOptions> ParticleType<T> resolveType(Key key) {
        Identifier id = Identifier.fromNamespaceAndPath(key.namespace(), key.value());
        ParticleType<?> type = BuiltInRegistries.PARTICLE_TYPE.get(id).map(Holder::value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown particle: " + key.asString()));

        return (ParticleType<T>) type;
    }
}
