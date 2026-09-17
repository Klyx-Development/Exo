package org.klyx.exo.paper.meta;

import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Rotations;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.entity.animal.golem.CopperGolemState;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.npc.villager.VillagerData;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import org.klyx.exo.common.MetaSerializerRegistry;
import org.klyx.exo.entity.data.ExoItemStack;
import org.klyx.exo.entity.meta.ExoBlockPos;
import org.klyx.exo.entity.meta.ExoBlockState;
import org.klyx.exo.entity.meta.ExoParticle;
import org.klyx.exo.entity.meta.ExoProfile;
import org.klyx.exo.entity.meta.ExoRotations;
import org.klyx.exo.entity.meta.ExoVariant;
import org.klyx.exo.entity.meta.ExoVillagerData;
import org.klyx.exo.entity.meta.impl.MetaType;
import org.klyx.exo.paper.util.PaperRegistryAccess;
import org.klyx.exo.util.Key;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class PaperMetaSerializers implements MetaSerializerRegistry<EntityDataSerializer<?>> {

    public static final PaperMetaSerializers INSTANCE = new PaperMetaSerializers();

    @Override
    public EntityDataSerializer<?> serializerFor(MetaType type) {
        return switch (type) {
            case BYTE -> EntityDataSerializers.BYTE;
            case INT -> EntityDataSerializers.INT;
            case FLOAT -> EntityDataSerializers.FLOAT;
            case BOOLEAN -> EntityDataSerializers.BOOLEAN;
            case COMPONENT -> EntityDataSerializers.COMPONENT;
            case OPTIONAL_COMPONENT -> EntityDataSerializers.OPTIONAL_COMPONENT;
            case ITEM_STACK -> EntityDataSerializers.ITEM_STACK;
            case POSE -> EntityDataSerializers.POSE;
            case BLOCK_POS -> EntityDataSerializers.BLOCK_POS;
            case OPTIONAL_BLOCK_POS -> EntityDataSerializers.OPTIONAL_BLOCK_POS;
            case DIRECTION -> EntityDataSerializers.DIRECTION;
            case ROTATIONS -> EntityDataSerializers.ROTATIONS;
            case QUATERNION -> EntityDataSerializers.QUATERNION;
            case VECTOR3 -> EntityDataSerializers.VECTOR3;
            case BLOCK_STATE -> EntityDataSerializers.BLOCK_STATE;
            case PARTICLE -> EntityDataSerializers.PARTICLE;
            case PARTICLES -> EntityDataSerializers.PARTICLES;
            case HUMANOID_ARM -> EntityDataSerializers.HUMANOID_ARM;
            case RESOLVABLE_PROFILE -> EntityDataSerializers.RESOLVABLE_PROFILE;
            case OPTIONAL_UNSIGNED_INT -> EntityDataSerializers.OPTIONAL_UNSIGNED_INT;
            case LONG -> EntityDataSerializers.LONG;
            case STRING -> EntityDataSerializers.STRING;
            case OPTIONAL_BLOCK_STATE -> EntityDataSerializers.OPTIONAL_BLOCK_STATE;
            case OPTIONAL_UUID -> EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE;
            case VILLAGER_DATA -> EntityDataSerializers.VILLAGER_DATA;
            case CAT_VARIANT -> EntityDataSerializers.CAT_VARIANT;
            case CAT_SOUND_VARIANT -> EntityDataSerializers.CAT_SOUND_VARIANT;
            case CHICKEN_VARIANT -> EntityDataSerializers.CHICKEN_VARIANT;
            case CHICKEN_SOUND_VARIANT -> EntityDataSerializers.CHICKEN_SOUND_VARIANT;
            case COW_VARIANT -> EntityDataSerializers.COW_VARIANT;
            case COW_SOUND_VARIANT -> EntityDataSerializers.COW_SOUND_VARIANT;
            case WOLF_VARIANT -> EntityDataSerializers.WOLF_VARIANT;
            case WOLF_SOUND_VARIANT -> EntityDataSerializers.WOLF_SOUND_VARIANT;
            case FROG_VARIANT -> EntityDataSerializers.FROG_VARIANT;
            case PIG_VARIANT -> EntityDataSerializers.PIG_VARIANT;
            case PIG_SOUND_VARIANT -> EntityDataSerializers.PIG_SOUND_VARIANT;
            case ZOMBIE_NAUTILUS_VARIANT -> EntityDataSerializers.ZOMBIE_NAUTILUS_VARIANT;
            case PAINTING_VARIANT -> EntityDataSerializers.PAINTING_VARIANT;
            case SNIFFER_STATE -> EntityDataSerializers.SNIFFER_STATE;
            case ARMADILLO_STATE -> EntityDataSerializers.ARMADILLO_STATE;
            case COPPER_GOLEM_STATE -> EntityDataSerializers.COPPER_GOLEM_STATE;
            case WEATHERING_COPPER_STATE -> EntityDataSerializers.WEATHERING_COPPER_STATE;
        };
    }

    @SuppressWarnings("unchecked")
    public static Object toNmsValue(MetaType type, Object value) {
        return switch (type) {
            case COMPONENT -> PaperAdventure.asVanilla((Component) value);
            case OPTIONAL_COMPONENT -> ((Optional<Component>) value)
                    .map(PaperAdventure::asVanilla);
            case ITEM_STACK -> toNmsItemStack((ExoItemStack) value);
            case POSE -> toNmsPose((org.klyx.exo.entity.meta.Pose) value);
            case BLOCK_POS -> toNmsBlockPos((ExoBlockPos) value);
            case OPTIONAL_BLOCK_POS -> ((Optional<ExoBlockPos>) value).map(PaperMetaSerializers::toNmsBlockPos);
            case DIRECTION -> toNmsDirection((org.klyx.exo.entity.meta.Direction) value);
            case ROTATIONS -> toNmsRotations((ExoRotations) value);
            case BLOCK_STATE -> toNmsBlockState((ExoBlockState) value);
            case PARTICLE -> toNmsParticle((ExoParticle) value);
            case PARTICLES -> ((List<ExoParticle>) value).stream().map(PaperMetaSerializers::toNmsParticle).toList();
            case HUMANOID_ARM -> toNmsHumanoidArm((org.klyx.exo.entity.meta.HumanoidArm) value);
            case RESOLVABLE_PROFILE -> toNmsProfile((ExoProfile) value);
            case OPTIONAL_BLOCK_STATE -> ((Optional<ExoBlockState>) value).map(PaperMetaSerializers::toNmsBlockState);
            case OPTIONAL_UUID -> ((Optional<UUID>) value).map(EntityReference::of);
            case VILLAGER_DATA -> toNmsVillagerData((ExoVillagerData) value);
            case CAT_VARIANT -> resolveHolder(Registries.CAT_VARIANT, (ExoVariant) value);
            case CAT_SOUND_VARIANT -> resolveHolder(Registries.CAT_SOUND_VARIANT, (ExoVariant) value);
            case CHICKEN_VARIANT -> resolveHolder(Registries.CHICKEN_VARIANT, (ExoVariant) value);
            case CHICKEN_SOUND_VARIANT -> resolveHolder(Registries.CHICKEN_SOUND_VARIANT, (ExoVariant) value);
            case COW_VARIANT -> resolveHolder(Registries.COW_VARIANT, (ExoVariant) value);
            case COW_SOUND_VARIANT -> resolveHolder(Registries.COW_SOUND_VARIANT, (ExoVariant) value);
            case WOLF_VARIANT -> resolveHolder(Registries.WOLF_VARIANT, (ExoVariant) value);
            case WOLF_SOUND_VARIANT -> resolveHolder(Registries.WOLF_SOUND_VARIANT, (ExoVariant) value);
            case FROG_VARIANT -> resolveHolder(Registries.FROG_VARIANT, (ExoVariant) value);
            case PIG_VARIANT -> resolveHolder(Registries.PIG_VARIANT, (ExoVariant) value);
            case PIG_SOUND_VARIANT -> resolveHolder(Registries.PIG_SOUND_VARIANT, (ExoVariant) value);
            case ZOMBIE_NAUTILUS_VARIANT -> resolveHolder(Registries.ZOMBIE_NAUTILUS_VARIANT, (ExoVariant) value);
            case PAINTING_VARIANT -> resolveHolder(Registries.PAINTING_VARIANT, (ExoVariant) value);
            case SNIFFER_STATE -> Sniffer.State.valueOf(((org.klyx.exo.entity.meta.SnifferState) value).name());
            case ARMADILLO_STATE -> Armadillo.ArmadilloState.valueOf(((org.klyx.exo.entity.meta.ArmadilloState) value).name());
            case COPPER_GOLEM_STATE -> CopperGolemState.valueOf(((org.klyx.exo.entity.meta.CopperGolemState) value).name());
            case WEATHERING_COPPER_STATE -> WeatheringCopper.WeatherState.valueOf(((org.klyx.exo.entity.meta.WeatheringCopperState) value).name());
            default -> value;
        };
    }

    private static Identifier toIdentifier(Key key) {
        return Identifier.fromNamespaceAndPath(key.namespace(), key.value());
    }

    private static <T> Holder<T> resolveHolder(ResourceKey<Registry<T>> registryKey, ExoVariant variant) {
        return PaperRegistryAccess.get().lookupOrThrow(registryKey).get(toIdentifier(variant.key())).orElseThrow();
    }

    private static VillagerData toNmsVillagerData(ExoVillagerData data) {
        Holder<VillagerType> type = BuiltInRegistries.VILLAGER_TYPE.get(toIdentifier(data.type())).orElseThrow();
        Holder<VillagerProfession> profession = BuiltInRegistries.VILLAGER_PROFESSION.get(toIdentifier(data.profession())).orElseThrow();
        return new VillagerData(type, profession, data.level());
    }

    private static ItemStack toNmsItemStack(ExoItemStack item) {
        return PaperItemStacks.toNms(item);
    }

    private static Pose toNmsPose(org.klyx.exo.entity.meta.Pose pose) {
        return Pose.valueOf(pose.name());
    }

    private static BlockPos toNmsBlockPos(ExoBlockPos pos) {
        return new BlockPos(pos.x(), pos.y(), pos.z());
    }

    private static Direction toNmsDirection(org.klyx.exo.entity.meta.Direction direction) {
        return Direction.valueOf(direction.name());
    }

    private static Rotations toNmsRotations(ExoRotations rotations) {
        return new Rotations(rotations.x(), rotations.y(), rotations.z());
    }

    private static BlockState toNmsBlockState(ExoBlockState state) {
        return PaperBlockStates.toNms(state);
    }

    private static ParticleOptions toNmsParticle(ExoParticle particle) {
        return PaperParticles.toNms(particle);
    }

    private static HumanoidArm toNmsHumanoidArm(org.klyx.exo.entity.meta.HumanoidArm arm) {
        return HumanoidArm.valueOf(arm.name());
    }

    private static ResolvableProfile toNmsProfile(ExoProfile profile) {
        return PaperProfiles.toNms(profile);
    }

}
