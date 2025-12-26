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

    public static org.bukkit.entity.Pose minecraftToBukkit(Pose pose) {
        return switch (pose) {
            case STANDING -> org.bukkit.entity.Pose.STANDING;
            case CROUCHING -> org.bukkit.entity.Pose.SNEAKING;
            case FALL_FLYING -> org.bukkit.entity.Pose.FALL_FLYING;
            case SLEEPING -> org.bukkit.entity.Pose.SLEEPING;
            case DYING -> org.bukkit.entity.Pose.DYING;
            case SWIMMING -> org.bukkit.entity.Pose.SWIMMING;
            case DIGGING -> org.bukkit.entity.Pose.DIGGING;
            case ROARING -> org.bukkit.entity.Pose.ROARING;
            case SITTING -> org.bukkit.entity.Pose.SITTING;
            case SLIDING -> org.bukkit.entity.Pose.SLIDING;
            case CROAKING -> org.bukkit.entity.Pose.CROAKING;
            case EMERGING -> org.bukkit.entity.Pose.EMERGING;
            case INHALING -> org.bukkit.entity.Pose.INHALING;
            case SHOOTING -> org.bukkit.entity.Pose.SHOOTING;
            case SNIFFING -> org.bukkit.entity.Pose.SNIFFING;
            case SPIN_ATTACK -> org.bukkit.entity.Pose.SPIN_ATTACK;
            case LONG_JUMPING -> org.bukkit.entity.Pose.LONG_JUMPING;
            case USING_TONGUE -> org.bukkit.entity.Pose.USING_TONGUE;
        };
    }

}
