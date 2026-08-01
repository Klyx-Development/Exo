package org.klyx.exo.util.packet.impl.handlers;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import org.klyx.exo.util.packet.PacketHelper;
import org.klyx.exo.util.packet.impl.Packets;


import java.util.List;

public class ChannelPacketHandler extends ChannelDuplexHandler {

    private final Packets packets;
    private final Connection connection;

    public ChannelPacketHandler(Packets packets, Connection connection) {
        this.packets = packets;
        this.connection = connection;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        // Packet Events supports sending hidden packets which are sent as direct byte buffers instead
        // of packets. To avoid breaking them we need to ignore non-packet objects.
        if (!(msg instanceof Packet<?> packetMsg)) {
            super.write(ctx, msg, promise);
            return;
        }

        List<Packet<?>> packets = this.packets.handlePacket(connection, packetMsg, false);
        if (packets.isEmpty()) {
            return;
        } else if (packets.size() == 1) {
            super.write(ctx, packets.getFirst(), promise);
        } else {
            // Create a new bundle packet containing all packets, we are always in the play
            // phase when this packet API is running anyway!
            super.write(
                    ctx,
                    PacketHelper.bundleIfNecessary(packets),
                    promise
            );
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof Packet<?> packetMsg)) {
            super.channelRead(ctx, msg);
            return;
        }

        List<Packet<?>> packets = this.packets.handlePacket(connection, packetMsg, true);
        if (packets.isEmpty()) {
            return;
        } else if (packets.size() == 1) {
            super.channelRead(ctx, packets.getFirst());
        } else {
            throw new IllegalStateException("Multiple packet result not supported for incoming packets");
        }
    }
}
