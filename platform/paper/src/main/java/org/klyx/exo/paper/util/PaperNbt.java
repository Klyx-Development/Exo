package org.klyx.exo.paper.util;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.ByteArrayBinaryTag;
import net.kyori.adventure.nbt.ByteBinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.DoubleBinaryTag;
import net.kyori.adventure.nbt.EndBinaryTag;
import net.kyori.adventure.nbt.FloatBinaryTag;
import net.kyori.adventure.nbt.IntArrayBinaryTag;
import net.kyori.adventure.nbt.IntBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import net.kyori.adventure.nbt.LongArrayBinaryTag;
import net.kyori.adventure.nbt.LongBinaryTag;
import net.kyori.adventure.nbt.ShortBinaryTag;
import net.kyori.adventure.nbt.StringBinaryTag;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

import java.util.Objects;

public final class PaperNbt {

    public static BinaryTag toBinaryTag(Tag tag) {
        return switch (tag) {
            case EndTag ignored -> EndBinaryTag.endBinaryTag();
            case ByteTag t -> ByteBinaryTag.byteBinaryTag(t.value());
            case ShortTag t -> ShortBinaryTag.shortBinaryTag(t.value());
            case IntTag t -> IntBinaryTag.intBinaryTag(t.value());
            case LongTag t -> LongBinaryTag.longBinaryTag(t.value());
            case FloatTag t -> FloatBinaryTag.floatBinaryTag(t.value());
            case DoubleTag t -> DoubleBinaryTag.doubleBinaryTag(t.value());
            case StringTag t -> StringBinaryTag.stringBinaryTag(t.value());
            case ByteArrayTag t -> ByteArrayBinaryTag.byteArrayBinaryTag(t.getAsByteArray());
            case IntArrayTag t -> IntArrayBinaryTag.intArrayBinaryTag(t.getAsIntArray());
            case LongArrayTag t -> LongArrayBinaryTag.longArrayBinaryTag(t.getAsLongArray());
            case ListTag t -> toBinaryList(t);
            case CompoundTag t -> toBinaryCompound(t);
        };
    }

    public static Tag toNmsTag(BinaryTag tag) {
        return switch (tag) {
            case EndBinaryTag ignored -> EndTag.INSTANCE;
            case ByteBinaryTag t -> ByteTag.valueOf(t.value());
            case ShortBinaryTag t -> ShortTag.valueOf(t.value());
            case IntBinaryTag t -> IntTag.valueOf(t.value());
            case LongBinaryTag t -> LongTag.valueOf(t.value());
            case FloatBinaryTag t -> FloatTag.valueOf(t.value());
            case DoubleBinaryTag t -> DoubleTag.valueOf(t.value());
            case StringBinaryTag t -> StringTag.valueOf(t.value());
            case ByteArrayBinaryTag t -> new ByteArrayTag(t.value());
            case IntArrayBinaryTag t -> new IntArrayTag(t.value());
            case LongArrayBinaryTag t -> new LongArrayTag(t.value());
            case ListBinaryTag t -> toNmsList(t);
            case CompoundBinaryTag t -> toNmsCompound(t);
        };
    }

    public static CompoundBinaryTag toBinaryCompound(CompoundTag tag) {
        CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();
        for (String key : tag.keySet()) {
            Tag value = tag.get(key);
            if (value != null) builder.put(key, toBinaryTag(value));
        }

        return builder.build();
    }

    public static CompoundTag toNmsCompound(CompoundBinaryTag tag) {
        CompoundTag compound = new CompoundTag();
        for (String key : tag.keySet()) {
            compound.put(key, toNmsTag(Objects.requireNonNull(tag.get(key))));
        }

        return compound;
    }

    private static ListBinaryTag toBinaryList(ListTag tag) {
        byte elementType = tag.identifyRawElementType();

        ListBinaryTag.Builder<BinaryTag> builder = ListBinaryTag.builder();
        for (Tag entry : tag) {
            builder.add(toBinaryTag(wrapIfNeeded(elementType, entry)));
        }
        return builder.build();
    }

    private static final byte TAG_COMPOUND_ID = 10;

    private static Tag wrapIfNeeded(byte elementType, Tag tag) {
        if (elementType != TAG_COMPOUND_ID) return tag;
        if (tag instanceof CompoundTag compound && !isWrapper(compound)) return compound;

        CompoundTag wrapper = new CompoundTag();
        wrapper.put("", tag);
        return wrapper;
    }

    private static boolean isWrapper(CompoundTag tag) {
        return tag.size() == 1 && tag.contains("");
    }

    private static ListTag toNmsList(ListBinaryTag tag) {
        ListTag list = new ListTag();
        for (BinaryTag entry : tag) {
            list.addAndUnwrap(toNmsTag(entry));
        }
        return list;
    }
}
