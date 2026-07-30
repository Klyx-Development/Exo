package org.klyx.exo.util.packet;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import xyz.bitsquidd.bits.log.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PacketHelper {
    private static final int MAX_PACKETS_PER_BUNDLE = 1024;

    public static Packet<?> bundleIfNecessary(Collection<Packet<?>> packets) {
        if (packets.isEmpty()) {
            throw new IllegalArgumentException("Cannot bundle an empty collection of packets!");
        }

        if (packets.size() == 1) {
            return packets.iterator().next();
        }

        return bundle(packets);
    }

    public static List<Packet<?>> bundleMultiple(Collection<Packet<?>> packets) {
        List<Packet<? super ClientGamePacketListener>> flatList = flattenPackets(packets);

        List<Packet<?>> bundles = new ArrayList<>();
        for (int i = 0; i < flatList.size(); i += MAX_PACKETS_PER_BUNDLE) {
            int end = Math.min(i + MAX_PACKETS_PER_BUNDLE, flatList.size());
            List<Packet<? super ClientGamePacketListener>> subList = flatList.subList(i, end);
            bundles.add(bundleIfNecessary(new ArrayList<>(subList)));
        }
        return bundles;
    }


    private static ClientboundBundlePacket bundle(Collection<Packet<?>> packets) {
        List<Packet<? super ClientGamePacketListener>> flatList = flattenPackets(packets);
        return new ClientboundBundlePacket(flatList);
    }

    @SuppressWarnings("unchecked")
    private static List<Packet<? super ClientGamePacketListener>> flattenPackets(Collection<Packet<?>> packets) {
        List<Packet<? super ClientGamePacketListener>> flatList = new ArrayList<>();
        for (Packet<?> packet : packets) {
            if (packet instanceof ClientboundBundlePacket bundle) {
                bundle.subPackets().forEach(flatList::add);
            } else {
                try {
                    flatList.add((Packet<? super ClientGamePacketListener>) packet);
                } catch (ClassCastException e) {
                    Logger.warn("Tried to bundle a packet of type " +
                            packet.getClass().getName() +
                            " which is not a ClientGamePacketListener packet. Skipping.");
                }
            }
        }
        return flatList;
    }
}
