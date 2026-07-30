package org.klyx.exo.util.packet;

import org.klyx.exo.entity.viewer.ChunkLoadPacketListener;
import org.klyx.exo.entity.viewer.ChunkUnloadPacketListener;
import org.klyx.exo.util.packet.impl.Packets;

public class PacketManager {

    public void register() {
        Packets.INSTANCE.register();

        new ChunkLoadPacketListener();
        new ChunkUnloadPacketListener();
    }

    public void unregister() {
        Packets.INSTANCE.unregister();
    }

}
