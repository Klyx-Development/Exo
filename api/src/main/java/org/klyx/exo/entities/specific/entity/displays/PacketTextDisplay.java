package org.klyx.exo.entities.specific.entity.displays;

import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.klyx.exo.data.keys.DataKeys;

public class PacketTextDisplay extends AbstractPacketDisplay {
    public PacketTextDisplay() {
        super(EntityType.TEXT_DISPLAY);
    }

    @Override
    public void applyExtraMetadata() {
        setMetadata(DataKeys.TextDisplay.TEXT);
        setMetadata(DataKeys.TextDisplay.LINE_WIDTH);
        setMetadata(DataKeys.TextDisplay.BACKGROUND_COLOR);
        setMetadata(DataKeys.TextDisplay.TEXT_OPACITY);
        setMetadata(DataKeys.TextDisplay.TEXT_DISPLAY_OPTIONS);
    }

    public void setText(Component text) {
        setMetadata(DataKeys.TextDisplay.TEXT, PaperAdventure.asVanilla(text));
    }

    public void setLineWidth(int width) {
        setMetadata(DataKeys.TextDisplay.LINE_WIDTH, width);
    }

    public void setBackgroundColor(int backgroundColor) {
        setMetadata(DataKeys.TextDisplay.BACKGROUND_COLOR, backgroundColor);
    }

}
