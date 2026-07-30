package org.klyx.exo.util.packet.impl.listener;

import net.minecraft.network.protocol.Packet;
import org.bukkit.entity.Player;

public interface PacketListener<P extends Packet<?>> {
    P processPacket(Player player, P packet);
}
