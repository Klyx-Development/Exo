package org.klyx.exo.data.keys;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.Pose;

import java.util.List;
import java.util.Optional;

public final class DataKeys {

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

    // LIVING ENTITY

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


    private DataKeys() {}
}
