package org.klyx.exo.paper.schedule;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.klyx.exo.common.TickScheduler;

public final class PaperTickScheduler implements TickScheduler {

    private final JavaPlugin plugin;

    public PaperTickScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Handle repeatEveryTick(Runnable task) {
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, task, 1L, 1L);
        return bukkitTask::cancel;
    }
}
