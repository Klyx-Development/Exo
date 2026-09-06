package org.klyx.exo.paper.packet.impl.listener;

import net.minecraft.network.protocol.Packet;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.UnknownNullability;

public interface PacketListener<P extends Packet<?>> {
    @UnknownNullability P processPacket(Player player, P packet);
}
