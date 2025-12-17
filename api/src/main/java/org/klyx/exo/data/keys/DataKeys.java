package org.klyx.exo.data.keys;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Rotations;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public final class DataKeys {

    public static final class Entity {
        public static final DataKey<Byte> FLAGS = DataKey.create(
                0,
                new EntityDataAccessor<>(0, EntityDataSerializers.BYTE),
                (byte) 0
        );

        public static final DataKey<Integer> AIR_TICKS = DataKey.create(
                1,
                new EntityDataAccessor<>(1, EntityDataSerializers.INT),
                300
        );

        public static final DataKey<Optional<Component>> CUSTOM_NAME = DataKey.create(
                2,
                new EntityDataAccessor<>(2, EntityDataSerializers.OPTIONAL_COMPONENT),
                Optional.empty()
        );

        public static final DataKey<Boolean> CUSTOM_NAME_VISIBLE = DataKey.create(
                3,
                new EntityDataAccessor<>(3, EntityDataSerializers.BOOLEAN),
                false
        );

        public static final DataKey<Boolean> SILENT = DataKey.create(
                4,
                new EntityDataAccessor<>(4, EntityDataSerializers.BOOLEAN),
                false
        );

        public static final DataKey<Boolean> NO_GRAVITY = DataKey.create(
                5,
                new EntityDataAccessor<>(5, EntityDataSerializers.BOOLEAN),
                false
        );

        public static final DataKey<Pose> POSE = DataKey.create(
                6,
                new EntityDataAccessor<>(6, EntityDataSerializers.POSE),
                Pose.STANDING
        );

        public static final DataKey<Integer> FROZEN_TICKS = DataKey.create(
                7,
                new EntityDataAccessor<>(7, EntityDataSerializers.INT),
                0
        );
    }

    public static final class AreaEffectCloud {
        public static final DataKey<Float> RADIUS = DataKey.create(
                8,
                new EntityDataAccessor<>(8, EntityDataSerializers.FLOAT),
                3.0f
        );

        public static final DataKey<Boolean> IGNORE_RADIUS = DataKey.create(
                9,
                new EntityDataAccessor<>(9, EntityDataSerializers.BOOLEAN),
                false
        );

        public static final DataKey<ParticleOptions> PARTICLE = DataKey.create(
                10,
                new EntityDataAccessor<>(10, EntityDataSerializers.PARTICLE),
                new DustParticleOptions(-1, 1f)
        );
    }

    public static final class EndCrystal {
        public static final DataKey<Optional<BlockPos>> BEAM_TARGET = DataKey.create(
                8,
                new EntityDataAccessor<>(8, EntityDataSerializers.OPTIONAL_BLOCK_POS),
                Optional.empty()
        );

        public static final DataKey<Boolean> SHOW_BOTTOM = DataKey.create(
                9,
                new EntityDataAccessor<>(9, EntityDataSerializers.BOOLEAN),
                true
        );
    }

    // EXPERIENCE ORB

    public static final class ExperienceOrb {
        public static final DataKey<Integer> EXPERIENCE_AMOUNT = DataKey.create(
                10,
                new EntityDataAccessor<>(10, EntityDataSerializers.INT),
                0
        );
    }

    public static final class EnderEye {
        public static final DataKey<ItemStack> ITEM_STACK = DataKey.create(
                8,
                new EntityDataAccessor<>(8, EntityDataSerializers.ITEM_STACK),
                ItemStack.EMPTY
        );
    }

    public static final class FallingBlock {
        public static final DataKey<BlockPos> POSITION = DataKey.create(
                8,
                new EntityDataAccessor<>(8, EntityDataSerializers.BLOCK_POS),
                new BlockPos(0, 0, 0)
        );
    }

    public static final class Fireball {
        public static final DataKey<ItemStack> ITEM_STACK = DataKey.create(
                8,
                new EntityDataAccessor<>(8, EntityDataSerializers.ITEM_STACK),
                ItemStack.EMPTY
        );
    }

    public static final class FireworkRocket {
        public static final DataKey<ItemStack> ITEM_STACK = DataKey.create(
                8,
                new EntityDataAccessor<>(8, EntityDataSerializers.ITEM_STACK),
                ItemStack.EMPTY
        );

        public static final DataKey<OptionalInt> LAUNCHER_ENTITY_ID = DataKey.create(
                9,
                new EntityDataAccessor<>(9, EntityDataSerializers.OPTIONAL_UNSIGNED_INT),
                OptionalInt.empty()
        );

        public static final DataKey<Boolean> SHOT_AT_ANGLE = DataKey.create(
                10,
                new EntityDataAccessor<>(10, EntityDataSerializers.BOOLEAN),
                false
        );
    }

    public static final class Interaction {
        public static final DataKey<Float> WIDTH = DataKey.create(
                8,
                new EntityDataAccessor<>(8, EntityDataSerializers.FLOAT),
                1.0f
        );

        public static final DataKey<Float> HEIGHT = DataKey.create(
                9,
                new EntityDataAccessor<>(9, EntityDataSerializers.FLOAT),
                1.0f
        );

        public static final DataKey<Boolean> CAN_BE_INTERACTED_WITH = DataKey.create(
                10,
                new EntityDataAccessor<>(10, EntityDataSerializers.BOOLEAN),
                false
        );
    }

    public static final class Item {
        public static final DataKey<ItemStack> ITEM_STACK = DataKey.create(
                8,
                new EntityDataAccessor<>(8, EntityDataSerializers.ITEM_STACK),
                ItemStack.EMPTY
        );
    }

    public static final class ItemFrame {
        public static final DataKey<Direction> DIRECTION = DataKey.create(
                8,
                new EntityDataAccessor<>(8, EntityDataSerializers.DIRECTION),
                Direction.SOUTH
        );

        public static final DataKey<ItemStack> ITEM_STACK = DataKey.create(
                9,
                new EntityDataAccessor<>(9, EntityDataSerializers.ITEM_STACK),
                ItemStack.EMPTY
        );

        public static final DataKey<Integer> ROTATION = DataKey.create(
                10,
                new EntityDataAccessor<>(10, EntityDataSerializers.INT),
                0
        );
    }

    public static final class OminousItemSpawner {
        public static final DataKey<ItemStack> ITEM_STACK = DataKey.create(
                8,
                new EntityDataAccessor<>(8, EntityDataSerializers.ITEM_STACK),
                ItemStack.EMPTY
        );
    }

    public static final class PrimedTNT {
        public static final DataKey<Integer> FUSE_TICKS = DataKey.create(
                8,
                new EntityDataAccessor<>(8, EntityDataSerializers.INT),
                80
        );

        public static final DataKey<BlockState> BLOCK_STATE = DataKey.create(
                9,
                new EntityDataAccessor<>(9, EntityDataSerializers.BLOCK_STATE),
                Blocks.TNT.defaultBlockState()
        );
    }

    public static final class Display {
        public static final DataKey<Integer> INTERPOLATION_DELAY = DataKey.create(
                8,
                new EntityDataAccessor<>(8, EntityDataSerializers.INT),
                0
        );

        public static final DataKey<Integer> TRANSFORMATION_INTERPOLATION_DURATION = DataKey.create(
                9,
                new EntityDataAccessor<>(9, EntityDataSerializers.INT),
                0
        );

        public static final DataKey<Integer> TELEPORT_DURATION = DataKey.create(
                10,
                new EntityDataAccessor<>(10, EntityDataSerializers.INT),
                0
        );

        public static final DataKey<Vector3f> TRANSLATION = DataKey.create(
                11,
                new EntityDataAccessor<>(11, EntityDataSerializers.VECTOR3),
                new Vector3f(0, 0, 0)
        );

        public static final DataKey<Vector3f> SCALE = DataKey.create(
                12,
                new EntityDataAccessor<>(12, EntityDataSerializers.VECTOR3),
                new Vector3f(1, 1, 1)
        );

        public static final DataKey<Quaternionf> ROTATION_LEFT = DataKey.create(
                13,
                new EntityDataAccessor<>(13, EntityDataSerializers.QUATERNION),
                new Quaternionf(0, 0, 0, 1)
        );

        public static final DataKey<Quaternionf> ROTATION_RIGHT = DataKey.create(
                14,
                new EntityDataAccessor<>(14, EntityDataSerializers.QUATERNION),
                new Quaternionf(0, 0, 0, 1)
        );

        public static final DataKey<Byte> BILLBOARD_CONSTRAINTS = DataKey.create(
                15,
                new EntityDataAccessor<>(15, EntityDataSerializers.BYTE),
                (byte) 0
        );

        public static final DataKey<Integer> BRIGHTNESS_OVERRIDE = DataKey.create(
                16,
                new EntityDataAccessor<>(16, EntityDataSerializers.INT),
                -1
        );

        public static final DataKey<Float> VIEW_RANGE = DataKey.create(
                17,
                new EntityDataAccessor<>(17, EntityDataSerializers.FLOAT),
                1f
        );

        public static final DataKey<Float> SHADOW_RADIUS = DataKey.create(
                18,
                new EntityDataAccessor<>(18, EntityDataSerializers.FLOAT),
                0f
        );

        public static final DataKey<Float> SHADOW_STRENGTH = DataKey.create(
                19,
                new EntityDataAccessor<>(19, EntityDataSerializers.FLOAT),
                1f
        );

        public static final DataKey<Float> WIDTH = DataKey.create(
                20,
                new EntityDataAccessor<>(20, EntityDataSerializers.FLOAT),
                0f
        );

        public static final DataKey<Float> HEIGHT = DataKey.create(
                21,
                new EntityDataAccessor<>(21, EntityDataSerializers.FLOAT),
                0f
        );

        public static final DataKey<Integer> GLOW_COLOR_OVERRIDE = DataKey.create(
                22,
                new EntityDataAccessor<>(22, EntityDataSerializers.INT),
                -1
        );
    }

    public static final class BlockDisplay {
        public static final DataKey<BlockState> BLOCK_STATE = DataKey.create(
                23,
                new EntityDataAccessor<>(23, EntityDataSerializers.BLOCK_STATE),
                Blocks.AIR.defaultBlockState()
        );
    }

    public static final class ItemDisplay {
        public static final DataKey<ItemStack> ITEM_STACK = DataKey.create(
                23,
                new EntityDataAccessor<>(23, EntityDataSerializers.ITEM_STACK),
                ItemStack.EMPTY
        );

        public static final DataKey<Byte> DISPLAY_TRANSFORM = DataKey.create(
                24,
                new EntityDataAccessor<>(24, EntityDataSerializers.BYTE),
                (byte) 0
        );
    }

    public static final class TextDisplay {
        public static final DataKey<Component> TEXT = DataKey.create(
                23,
                new EntityDataAccessor<>(23, EntityDataSerializers.COMPONENT),
                Component.empty()
        );

        public static final DataKey<Integer> LINE_WIDTH = DataKey.create(
                24,
                new EntityDataAccessor<>(24, EntityDataSerializers.INT),
                200
        );

        public static final DataKey<Integer> BACKGROUND_COLOR = DataKey.create(
                25,
                new EntityDataAccessor<>(25, EntityDataSerializers.INT),
                1073741824
        );

        public static final DataKey<Byte> TEXT_OPACITY = DataKey.create(
                26,
                new EntityDataAccessor<>(26, EntityDataSerializers.BYTE),
                (byte) -1
        );

        // TODO make metadata enum
        public static final DataKey<Byte> TEXT_DISPLAY_OPTIONS = DataKey.create(
                27,
                new EntityDataAccessor<>(27, EntityDataSerializers.BYTE),
                (byte) 0
        );
    }

    public static final class LivingEntity {
        public static final DataKey<Byte> HAND_STATES = DataKey.create(
                8,
                new EntityDataAccessor<>(8, EntityDataSerializers.BYTE),
                (byte) 0
        );

        public static final DataKey<Float> HEALTH = DataKey.create(
                9,
                new EntityDataAccessor<>(9, EntityDataSerializers.FLOAT),
                1.0f
        );

        public static final DataKey<List<ParticleOptions>> PARTICLES = DataKey.create(
                10,
                new EntityDataAccessor<>(10, EntityDataSerializers.PARTICLES),
                List.of()
        );

        public static final DataKey<Boolean> POTION_EFFECT_AMBIENT = DataKey.create(
                11,
                new EntityDataAccessor<>(11, EntityDataSerializers.BOOLEAN),
                false
        );

        public static final DataKey<Integer> ARROWS_IN_ENTITY = DataKey.create(
                12,
                new EntityDataAccessor<>(12, EntityDataSerializers.INT),
                0
        );

        public static final DataKey<Integer> BEE_STINGERS_IN_ENTITY = DataKey.create(
                13,
                new EntityDataAccessor<>(13, EntityDataSerializers.INT),
                0
        );

        public static final DataKey<Optional<BlockPos>> BED_POSITION = DataKey.create(
                14,
                new EntityDataAccessor<>(14, EntityDataSerializers.OPTIONAL_BLOCK_POS),
                Optional.empty()
        );
    }

    public static final class ArmorStand {
        public static final DataKey<Byte> ARMOR_STAND_OPTIONS = DataKey.create(
                15,
                new EntityDataAccessor<>(15, EntityDataSerializers.BYTE),
                (byte) 0
        );

        public static final DataKey<Rotations> HEAD_ROTATION = DataKey.create(
                16,
                new EntityDataAccessor<>(16, EntityDataSerializers.ROTATIONS),
                new Rotations(0, 0, 0)
        );

        public static final DataKey<Rotations> BODY_ROTATION = DataKey.create(
                17,
                new EntityDataAccessor<>(17, EntityDataSerializers.ROTATIONS),
                new Rotations(0, 0, 0)
        );

        public static final DataKey<Rotations> LEFT_ARM_ROTATION = DataKey.create(
                18,
                new EntityDataAccessor<>(18, EntityDataSerializers.ROTATIONS),
                new Rotations(-10, 0, -10)
        );

        public static final DataKey<Rotations> RIGHT_ARM_ROTATION = DataKey.create(
                19,
                new EntityDataAccessor<>(19, EntityDataSerializers.ROTATIONS),
                new Rotations(-15, 0, 10)
        );

        public static final DataKey<Rotations> LEFT_LEG_ROTATION = DataKey.create(
                20,
                new EntityDataAccessor<>(20, EntityDataSerializers.ROTATIONS),
                new Rotations(-1, 0, -1)
        );

        public static final DataKey<Rotations> RIGHT_LEG_ROTATION = DataKey.create(
                21,
                new EntityDataAccessor<>(21, EntityDataSerializers.ROTATIONS),
                new Rotations(1, 0, 1)
        );
    }

    public static final class Mob {
        public static final DataKey<Byte> MOB_FLAGS = DataKey.create(
                15,
                new EntityDataAccessor<>(15, EntityDataSerializers.BYTE),
                (byte) 0
        );
    }

    private DataKeys() {}
}
