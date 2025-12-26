package org.klyx.exo.data.metadata.flag;

import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.function.Consumer;

public class ByteFlagManager<T extends Enum<T> & FlagEnum> {

    private byte flags = 0;
    private final Class<T> enumClass;
    private final Consumer<Byte> metaToChange;

    public ByteFlagManager(Class<T> enumClass, Consumer<Byte> metaToChange) {
        this.enumClass = enumClass;
        this.metaToChange = metaToChange;
    }

    private void changeMeta() {
        if (metaToChange != null) metaToChange.accept(flags);
    }

    public void setFlag(@NotNull T flag) {
        byte oldFlags = flags;
        flags |= (byte) (1 << flag.getBitPosition());
        if (oldFlags != flags) {
            changeMeta();
        }
    }

    public void setFlag(@NotNull T flag, boolean value) {
        if (value) {
            setFlag(flag);
        } else {
            clearFlag(flag);
        }
    }

    public void clearFlag(@NotNull T flag) {
        byte oldFlags = flags;
        flags &= (byte) ~((1 << flag.getBitPosition()));
        if (oldFlags != flags) {
            changeMeta();
        }
    }

    public void toggleFlag(@NotNull T flag) {
        byte oldFlags = flags;
        flags ^= (byte) (1 << flag.getBitPosition());
        if (oldFlags != flags) {
            changeMeta();
        }
    }

    public boolean hasFlag(@NotNull T flag) {
        return (flags & (1 << flag.getBitPosition())) != 0;
    }

    @SafeVarargs
    public final void setFlags(@NotNull T... flagsToSet) {
        byte oldFlags = flags;
        for (T flag : flagsToSet) {
            flags |= (byte) (1 << flag.getBitPosition());
        }
        if (oldFlags != flags) {
            changeMeta();
        }
    }

    @SafeVarargs
    public final void clearFlags(@NotNull T... flagsToClear) {
        byte oldFlags = flags;
        for (T flag : flagsToClear) {
            flags &= (byte) ~((1 << flag.getBitPosition()));
        }
        if (oldFlags != flags) {
            changeMeta();
        }
    }

    public void clearAll() {
        if (flags != 0) {
            flags = 0;
            changeMeta();
        }
    }

    public byte getFlags() {
        return flags;
    }

    public void setFlags(byte flags) {
        if (this.flags != flags) {
            this.flags = flags;
            changeMeta();
        }
    }

    public EnumSet<T> getActiveFlags() {
        EnumSet<T> activeFlags = EnumSet.noneOf(enumClass);
        for (T flag : enumClass.getEnumConstants()) {
            if (hasFlag(flag)) {
                activeFlags.add(flag);
            }
        }
        return activeFlags;
    }
}
