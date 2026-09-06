package org.klyx.exo.minestom.schedule;

import net.minestom.server.MinecraftServer;
import net.minestom.server.timer.Task;
import net.minestom.server.timer.TaskSchedule;
import org.klyx.exo.common.TickScheduler;

public final class MinestomTickScheduler implements TickScheduler {

    @Override
    public Handle repeatEveryTick(Runnable task) {
        Task minestomTask = MinecraftServer.getSchedulerManager()
                .buildTask(task)
                .delay(TaskSchedule.tick(1))
                .repeat(TaskSchedule.tick(1))
                .schedule();
        return minestomTask::cancel;
    }
}
