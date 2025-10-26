package org.klyx.exo.utils;

import net.minecraft.world.entity.Pose;

public class ConversionUtil {

    public static Pose bukkitToMinecraft(org.bukkit.entity.Pose pose) {
        return switch (pose) {
            case STANDING -> Pose.STANDING;
            case SNEAKING -> Pose.CROUCHING;
            case FALL_FLYING -> Pose.FALL_FLYING;
            case SLEEPING -> Pose.SLEEPING;
            case DYING -> Pose.DYING;
            case SWIMMING -> Pose.SWIMMING;
            case DIGGING -> Pose.DIGGING;
            case ROARING -> Pose.ROARING;
            case SITTING -> Pose.SITTING;
            case SLIDING -> Pose.SLIDING;
            case CROAKING -> Pose.CROAKING;
            case EMERGING -> Pose.EMERGING;
            case INHALING -> Pose.INHALING;
            case SHOOTING -> Pose.SHOOTING;
            case SNIFFING -> Pose.SNIFFING;
            case SPIN_ATTACK -> Pose.SPIN_ATTACK;
            case LONG_JUMPING -> Pose.LONG_JUMPING;
            case USING_TONGUE -> Pose.USING_TONGUE;
        };
    }

}
