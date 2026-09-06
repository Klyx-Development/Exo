package org.klyx.exo.minestom.demo;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.timer.TaskSchedule;
import org.klyx.exo.entity.ExoPos;
import org.klyx.exo.minestom.ExoMinestom;
import org.klyx.exo.minestom.player.MinestomExoPlayer;
import org.klyx.exo.minestom.world.MinestomExoWorld;

public final class ExoMinestomDemo {

    static void main() {
        MinecraftServer server = MinecraftServer.init();

        InstanceContainer instance = MinecraftServer.getInstanceManager().createInstanceContainer();
        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));

        ExoMinestom.init();

        GlobalEventHandler events = MinecraftServer.getGlobalEventHandler();
        events.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            event.setSpawningInstance(instance);
            event.getPlayer().setRespawnPoint(new Pos(0, 42, 0));
        });

        events.addListener(PlayerSpawnEvent.class, event -> {
            FakeZombieMinestomTest fake = new FakeZombieMinestomTest();
            fake.spawn(MinestomExoWorld.of(instance), new ExoPos(0, 42, 5, 0, 0));
            fake.addViewer(MinestomExoPlayer.of(event.getPlayer()).uuid());

            MinecraftServer.getSchedulerManager().buildTask(() -> fake.setYaw(fake.getYaw() + 5f))
                    .delay(TaskSchedule.tick(1))
                    .repeat(TaskSchedule.tick(2))
                    .schedule();
        });

        server.start("0.0.0.0", 25565);
    }
}
