package org.klyx.exo.util.packet.impl.handlers;

import net.minecraft.network.protocol.Packet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

public class PacketHandlers<P extends Packet<?>> {

    private final Class<P> packet;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final List<PriorityPacketHandler<?, P>> handlers = new ArrayList<>();

    public PacketHandlers(Class<P> packet) {
        this.packet = packet;
    }

    public void assign(PriorityPacketHandler<?, P> handler) {
        lock.writeLock().lock();
        try {
            handlers.add(handler);
            handlers.sort(null);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void unassign(PriorityPacketHandler<?, P> handler) {
        lock.writeLock().lock();
        try {
            handlers.remove(handler);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void read(Consumer<List<PriorityPacketHandler<?, P>>> operation) {
        lock.readLock().lock();
        try {
            operation.accept(handlers);
        } finally {
            lock.readLock().unlock();
        }
    }
}
