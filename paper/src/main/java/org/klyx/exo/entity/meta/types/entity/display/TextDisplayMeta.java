package org.klyx.exo.entity.meta.types.entity.display;

import io.papermc.paper.adventure.PaperAdventure;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import org.klyx.exo.entity.meta.impl.MetaAccessor;

public class TextDisplayMeta extends DisplayMeta {

    private static final MetaAccessor<Component> TEXT = new MetaAccessor<>(23, EntityDataSerializers.COMPONENT, Component.empty());
    private static final MetaAccessor<Integer> LINE_WIDTH = new MetaAccessor<>(24, EntityDataSerializers.INT, 200);
    private static final MetaAccessor<Integer> BACKGROUND_COLOR = new MetaAccessor<>(25, EntityDataSerializers.INT, 1073741824);
    private static final MetaAccessor<Byte> TEXT_OPACITY = new MetaAccessor<>(26, EntityDataSerializers.BYTE, (byte) -1);
    private static final MetaAccessor<Byte> STYLE_FLAGS = new MetaAccessor<>(27, EntityDataSerializers.BYTE, (byte) 0);

    public enum Align {
        CENTER(0b00000),
        LEFT(0b01000),
        RIGHT(0b10000);

        private static final byte MASK = 0b11000;

        private final byte bits;

        Align(int bits) {
            this.bits = (byte) bits;
        }

        byte bits() {
            return bits;
        }

        static byte mask() {
            return MASK;
        }

        static Align fromFlags(byte flags) {
            for (Align align : values()) {
                if (align == CENTER) continue; // check non-zero patterns first
                if ((flags & align.bits) == align.bits) return align;
            }
            return CENTER;
        }
    }

    public TextDisplayMeta setText(Component text) {
        set(TEXT, text);
        return this;
    }

    public TextDisplayMeta setText(net.kyori.adventure.text.Component text) {
        return setText(PaperAdventure.asVanilla(text));
    }

    public Component getText() {
        return get(TEXT);
    }

    public TextDisplayMeta setLineWidth(int lineWidth) {
        set(LINE_WIDTH, lineWidth);
        return this;
    }

    public int getLineWidth() {
        return get(LINE_WIDTH);
    }

    public TextDisplayMeta setBackgroundColor(int color) {
        set(BACKGROUND_COLOR, color);
        return this;
    }

    public int getBackgroundColor() {
        return get(BACKGROUND_COLOR);
    }

    public TextDisplayMeta setTextOpacity(int opacity) {
        set(TEXT_OPACITY, (byte) opacity);
        return this;
    }

    public int getTextOpacity() {
        return get(TEXT_OPACITY);
    }

    public TextDisplayMeta setShadow(boolean shadow) {
        setFlag(STYLE_FLAGS, 0, shadow);
        return this;
    }

    public boolean hasShadow() {
        return getFlag(STYLE_FLAGS, 0);
    }

    public TextDisplayMeta setSeeThrough(boolean seeThrough) {
        setFlag(STYLE_FLAGS, 1, seeThrough);
        return this;
    }

    public boolean isSeeThrough() {
        return getFlag(STYLE_FLAGS, 1);
    }

    public TextDisplayMeta setDefaultBackground(boolean defaultBackground) {
        setFlag(STYLE_FLAGS, 2, defaultBackground);
        return this;
    }

    public boolean hasDefaultBackground() {
        return getFlag(STYLE_FLAGS, 2);
    }

    public TextDisplayMeta setAlign(Align align) {
        byte current = get(STYLE_FLAGS);
        byte updated = (byte) ((current & ~Align.mask()) | align.bits());
        set(STYLE_FLAGS, updated);
        return this;
    }

    public Align getAlign() {
        return Align.fromFlags(get(STYLE_FLAGS));
    }

}