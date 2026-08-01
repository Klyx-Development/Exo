package org.klyx.exo.util.packet.impl;

import net.minecraft.network.protocol.Packet;

import java.util.List;

@FunctionalInterface
public interface PacketFunction<R, P extends Packet<?>> {
    List<Packet<?>> apply(R receiver, P packet);
}