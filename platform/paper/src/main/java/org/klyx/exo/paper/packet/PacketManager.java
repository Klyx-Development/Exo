package org.klyx.exo.paper.packet;

import org.klyx.exo.paper.entity.viewer.AttackPacketListener;
import org.klyx.exo.paper.entity.viewer.ChunkLoadPacketListener;
import org.klyx.exo.paper.entity.viewer.ChunkUnloadPacketListener;
import org.klyx.exo.paper.entity.viewer.InteractPacketListener;
import org.klyx.exo.paper.packet.impl.Packets;

public class PacketManager {

    public void register() {
        Packets.INSTANCE.register();

        new ChunkLoadPacketListener();
        new ChunkUnloadPacketListener();
        new AttackPacketListener();
        new InteractPacketListener();
    }

    public void unregister() {
        Packets.INSTANCE.unregister();
    }

}
