package org.klyx.exo.entities.specific.entity.displays;

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
}
