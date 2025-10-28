package org.klyx.exo.data.keys;

import io.papermc.paper.registry.PaperRegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.key.Key;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.decoration.PaintingVariants;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Art;
import org.bukkit.craftbukkit.CraftArt;
import org.bukkit.craftbukkit.entity.CraftPainting;

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

    public static final class LargeFireball {
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
                new EntityDataAccessor<>(8, EntityDataSerializers.OPTIONAL_UNSIGNED_INT),
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

    public static final class Painting {
        public static final DataKey<Direction> DIRECTION = DataKey.create(
                8,
                new EntityDataAccessor<>(8, EntityDataSerializers.DIRECTION),
                Direction.SOUTH
        );

        public static final DataKey<Holder<PaintingVariant>> PAINTING_VARIANT = DataKey.create(
                9,
                new EntityDataAccessor<>(8, EntityDataSerializers.PAINTING_VARIANT),
                CraftArt.bukkitToMinecraftHolder((Art) PaperRegistryAccess.instance().getRegistry(RegistryKey.PAINTING_VARIANT))
        );
    }

    // LIVING ENTITY

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

    // MOB

    public static final class Mob {
        public static final DataKey<Byte> MOB_FLAGS = DataKey.create(
                15,
                new EntityDataAccessor<>(15, EntityDataSerializers.BYTE),
                (byte) 0
        );
    }

    private DataKeys() {}
}
