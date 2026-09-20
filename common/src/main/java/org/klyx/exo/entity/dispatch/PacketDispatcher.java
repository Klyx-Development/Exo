package org.klyx.exo.entity.dispatch;

import org.jspecify.annotations.Nullable;
import org.klyx.exo.common.TickScheduler;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class PacketDispatcher<P> {

    private final PacketSender<P> sender;
    private final TickScheduler scheduler;
    private final ExecutorService executor = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "ExoPackets");
        thread.setDaemon(true);
        return thread;
    });

    private Map<UUID, EnumMap<PacketCategory, List<P>>> pending = new HashMap<>();
    private TickScheduler.@Nullable Handle tickHandle;

    public PacketDispatcher(PacketSender<P> sender, TickScheduler scheduler) {
        this.sender = sender;
        this.scheduler = scheduler;
    }

    public void schedule(UUID viewer, PacketCategory category, List<P> packets) {
        if (packets.isEmpty()) return;

        executor.execute(() -> pending.computeIfAbsent(viewer, _ -> new EnumMap<>(PacketCategory.class))
                .computeIfAbsent(category, _ -> new ArrayList<>())
                .addAll(packets));
    }

    public void start() {
        if (tickHandle == null) tickHandle = scheduler.repeatEveryTick(() -> executor.execute(this::drain));
    }

    public void stop() {
        if (tickHandle != null) {
            tickHandle.cancel();
            tickHandle = null;
        }
        executor.execute(() -> pending = new HashMap<>());
        executor.shutdown();
    }

    private void drain() {
        if (pending.isEmpty()) return;

        Map<UUID, EnumMap<PacketCategory, List<P>>> toSend = pending;
        pending = new HashMap<>();

        sendAll(toSend);
    }

    private void sendAll(Map<UUID, EnumMap<PacketCategory, List<P>>> toSend) {
        for (Map.Entry<UUID, EnumMap<PacketCategory, List<P>>> entry : toSend.entrySet()) {
            try {
                List<P> ordered = new ArrayList<>();
                for (PacketCategory category : PacketCategory.values()) {
                    List<P> categoryPackets = entry.getValue().get(category);
                    if (categoryPackets != null) ordered.addAll(categoryPackets);
                }

                if (!ordered.isEmpty()) {
                    sender.sendBatch(entry.getKey(), ordered);
                }

            } catch (Exception e) {
                sender.onFlushError(entry.getKey(), e);
            }
        }
    }

}
