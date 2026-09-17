package org.klyx.exo.minestom.meta;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.metadata.animal.ArmadilloMeta;
import net.minestom.server.entity.metadata.animal.SnifferMeta;
import net.minestom.server.entity.metadata.golem.CopperGolemMeta;
import net.minestom.server.entity.metadata.villager.VillagerMeta;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.EntityPose;
import net.minestom.server.entity.MainHand;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.VillagerProfession;
import net.minestom.server.entity.VillagerType;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.player.ResolvableProfile;
import net.minestom.server.particle.Particle;
import net.minestom.server.registry.RegistryKey;
import net.minestom.server.utils.Direction;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.klyx.exo.entity.data.ExoItemStack;
import org.klyx.exo.entity.meta.ExoBlockPos;
import org.klyx.exo.entity.meta.ExoBlockState;
import org.klyx.exo.entity.meta.ExoParticle;
import org.klyx.exo.entity.meta.ExoProfile;
import org.klyx.exo.entity.meta.ExoRotations;
import org.klyx.exo.entity.meta.ExoVariant;
import org.klyx.exo.entity.meta.ExoVillagerData;
import org.klyx.exo.entity.meta.impl.MetaType;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public final class MinestomMetaSerializers {

    @SuppressWarnings("unchecked")
    public static Metadata.Entry<?> toEntry(MetaType type, Object value) {
        return switch (type) {
            case BYTE -> Metadata.Byte((Byte) value);
            case INT -> Metadata.VarInt((Integer) value);
            case FLOAT -> Metadata.Float((Float) value);
            case BOOLEAN -> Metadata.Boolean((Boolean) value);
            case COMPONENT -> Metadata.Component((Component) value);
            case OPTIONAL_COMPONENT -> Metadata.OptComponent(((Optional<Component>) value).orElse(null));
            case ITEM_STACK -> Metadata.ItemStack(toMinestomItemStack((ExoItemStack) value));
            case POSE -> Metadata.Pose(toMinestomPose((org.klyx.exo.entity.meta.Pose) value));
            case BLOCK_POS -> Metadata.BlockPosition(toPoint((ExoBlockPos) value));
            case OPTIONAL_BLOCK_POS -> Metadata.OptBlockPosition(
                    ((Optional<ExoBlockPos>) value).map(MinestomMetaSerializers::toPoint).orElse(null));
            case DIRECTION -> Metadata.Direction(toMinestomDirection((org.klyx.exo.entity.meta.Direction) value));
            case ROTATIONS -> Metadata.Rotation(toPoint((ExoRotations) value));
            case QUATERNION -> Metadata.Quaternion(toFloatArray((Quaternionf) value));
            case VECTOR3 -> Metadata.Vector3(toPoint((Vector3f) value));
            case BLOCK_STATE -> Metadata.BlockState(toMinestomBlock((ExoBlockState) value));
            case PARTICLE -> Metadata.Particle(toMinestomParticle((ExoParticle) value));
            case PARTICLES -> Metadata.ParticleList(((List<ExoParticle>) value).stream()
                    .map(MinestomMetaSerializers::toMinestomParticle).toList());
            case HUMANOID_ARM -> Metadata.MainHand(toMainHand((org.klyx.exo.entity.meta.HumanoidArm) value));
            case RESOLVABLE_PROFILE -> Metadata.ResolvableProfile(toMinestomProfile((ExoProfile) value));
            case OPTIONAL_UNSIGNED_INT -> Metadata.OptVarInt((Integer) value);
            case LONG -> Metadata.VarLong((Long) value);
            case STRING -> Metadata.String((String) value);
            case OPTIONAL_BLOCK_STATE -> Metadata.OptBlockState(
                    ((Optional<ExoBlockState>) value).map(MinestomMetaSerializers::toMinestomBlock).orElse(null));
            case OPTIONAL_UUID -> Metadata.OptUUID(((Optional<UUID>) value).orElse(null));
            case VILLAGER_DATA -> Metadata.VillagerData(toMinestomVillagerData((ExoVillagerData) value));
            case CAT_VARIANT -> Metadata.CatVariant(toRegistryKey((ExoVariant) value));
            case CAT_SOUND_VARIANT -> Metadata.CatSoundVariant(toRegistryKey((ExoVariant) value));
            case CHICKEN_VARIANT -> Metadata.ChickenVariant(toRegistryKey((ExoVariant) value));
            case CHICKEN_SOUND_VARIANT -> Metadata.ChickenSoundVariant(toRegistryKey((ExoVariant) value));
            case COW_VARIANT -> Metadata.CowVariant(toRegistryKey((ExoVariant) value));
            case COW_SOUND_VARIANT -> Metadata.CowSoundVariant(toRegistryKey((ExoVariant) value));
            case WOLF_VARIANT -> Metadata.WolfVariant(toRegistryKey((ExoVariant) value));
            case WOLF_SOUND_VARIANT -> Metadata.WolfSoundVariant(toRegistryKey((ExoVariant) value));
            case FROG_VARIANT -> Metadata.FrogVariant(toRegistryKey((ExoVariant) value));
            case PIG_VARIANT -> Metadata.PigVariant(toRegistryKey((ExoVariant) value));
            case PIG_SOUND_VARIANT -> Metadata.PigSoundVariant(toRegistryKey((ExoVariant) value));
            case ZOMBIE_NAUTILUS_VARIANT -> Metadata.ZombieNautilusVariant(toRegistryKey((ExoVariant) value));
            case PAINTING_VARIANT -> Metadata.PaintingVariant(toRegistryKey((ExoVariant) value));
            case SNIFFER_STATE -> Metadata.SnifferState(SnifferMeta.State.valueOf(((org.klyx.exo.entity.meta.SnifferState) value).name()));
            case ARMADILLO_STATE -> Metadata.ArmadilloState(ArmadilloMeta.State.valueOf(((org.klyx.exo.entity.meta.ArmadilloState) value).name()));
            case COPPER_GOLEM_STATE -> Metadata.CopperGolemState(CopperGolemMeta.State.valueOf(((org.klyx.exo.entity.meta.CopperGolemState) value).name()));
            case WEATHERING_COPPER_STATE -> Metadata.WeatherState(CopperGolemMeta.WeatherState.valueOf(((org.klyx.exo.entity.meta.WeatheringCopperState) value).name()));
        };
    }

    private static Key toMinestomKey(org.klyx.exo.util.Key key) {
        return Key.key(key.namespace(), key.value());
    }

    private static <T> RegistryKey<T> toRegistryKey(ExoVariant variant) {
        return RegistryKey.unsafeOf(toMinestomKey(variant.key()));
    }

    private static VillagerMeta.VillagerData toMinestomVillagerData(ExoVillagerData data) {
        VillagerType type = VillagerType.valueOf(data.type().value().toUpperCase(Locale.ROOT));
        VillagerProfession profession = VillagerProfession.fromKey(toMinestomKey(data.profession()));
        int level = Math.max(1, Math.min(5, data.level()));
        VillagerMeta.Level villagerLevel = VillagerMeta.Level.values()[level - 1];
        return new VillagerMeta.VillagerData(type, profession, villagerLevel);
    }

    private static net.minestom.server.item.ItemStack toMinestomItemStack(ExoItemStack item) {
        return MinestomItemStacks.toMinestom(item);
    }

    private static Block toMinestomBlock(ExoBlockState state) {
        return MinestomBlockStates.toMinestom(state);
    }

    private static Particle toMinestomParticle(ExoParticle particle) {
        return MinestomParticles.toMinestom(particle);
    }

    private static ResolvableProfile toMinestomProfile(ExoProfile profile) {
        return MinestomProfiles.toMinestom(profile);
    }

    private static Point toPoint(ExoBlockPos pos) {
        return new Vec(pos.x(), pos.y(), pos.z());
    }

    private static Point toPoint(ExoRotations rotations) {
        return new Vec(rotations.x(), rotations.y(), rotations.z());
    }

    private static Point toPoint(Vector3f vector) {
        return new Vec(vector.x(), vector.y(), vector.z());
    }

    private static float[] toFloatArray(Quaternionf quaternion) {
        return new float[]{quaternion.x(), quaternion.y(), quaternion.z(), quaternion.w()};
    }

    private static EntityPose toMinestomPose(org.klyx.exo.entity.meta.Pose pose) {
        if (pose == org.klyx.exo.entity.meta.Pose.CROUCHING) return EntityPose.SNEAKING;
        return EntityPose.valueOf(pose.name());
    }

    private static Direction toMinestomDirection(org.klyx.exo.entity.meta.Direction direction) {
        return Direction.valueOf(direction.name());
    }

    private static MainHand toMainHand(org.klyx.exo.entity.meta.HumanoidArm arm) {
        return MainHand.valueOf(arm.name());
    }
}
