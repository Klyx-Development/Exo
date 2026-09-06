package org.klyx.exo.paper.meta;

import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Rotations;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.state.BlockState;
import org.klyx.exo.common.MetaSerializerRegistry;
import org.klyx.exo.entity.data.ExoItemStack;
import org.klyx.exo.entity.meta.ExoBlockPos;
import org.klyx.exo.entity.meta.ExoBlockState;
import org.klyx.exo.entity.meta.ExoParticle;
import org.klyx.exo.entity.meta.ExoProfile;
import org.klyx.exo.entity.meta.ExoRotations;
import org.klyx.exo.entity.meta.impl.MetaType;

import java.util.List;
import java.util.Optional;

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
            default -> value;
        };
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
