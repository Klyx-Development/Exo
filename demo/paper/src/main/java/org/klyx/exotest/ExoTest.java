package org.klyx.exotest;

import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.klyx.exo.entity.ExoPos;
import org.klyx.exo.paper.entity.components.types.PassengerComponent;
import org.klyx.exo.entity.meta.types.entity.living.avatar.MannequinMeta;
import org.klyx.exo.entity.meta.types.entity.living.mob.creatures.ZombieMeta;
import org.klyx.exo.paper.ExoPaper;
import org.klyx.exo.paper.meta.PaperProfiles;
import org.klyx.exo.paper.player.ExoPaperPlayer;
import org.klyx.exo.paper.util.PaperLocUtil;
import org.klyx.exo.world.ExoWorld;

public class ExoTest extends JavaPlugin {

    @Override
    public void onEnable() {
        ExoPaper.init(this);

        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(
                    Commands.literal("testspawn")
                            .then(Commands.literal("zombie")
                                .executes(ctx -> {
                                    Location loc = ctx.getSource().getLocation();
                                    ExoWorld world = PaperLocUtil.toExoWorld(loc.getWorld());
                                    ExoPos pos = PaperLocUtil.toExoPos(loc);

                                    TestZombie zombie = new TestZombie();
                                    zombie.spawn(world, pos);
                                    zombie.addViewer(ctx.getSource().getExecutor().getUniqueId());

                                    if (!(ctx.getSource().getExecutor() instanceof Player player)) return Command.SINGLE_SUCCESS;
                                    zombie.getComponent(PassengerComponent.class).startRiding(player.getEntityId());

                                    this.getServer().getScheduler().runTaskLater(this, () -> {
                                        zombie.editMeta(ZombieMeta.class, meta -> meta.setOnFire(true));
                                    }, 20 * 5);

                                    this.getServer().getScheduler().runTaskLater(this, zombie::destroy, 20 * 20);

                                    return Command.SINGLE_SUCCESS;
                                }))
                            .then(Commands.literal("mannequin")
                                .executes(ctx -> {
                                    if (!(ctx.getSource().getExecutor() instanceof Player player)) return Command.SINGLE_SUCCESS;

                                    TestMannequin mannequin = new TestMannequin();
                                    ExoWorld world = PaperLocUtil.toExoWorld(player.getLocation().getWorld());
                                    ExoPos pos = PaperLocUtil.toExoPos(player.getLocation());

                                    mannequin.spawn(world, pos);
                                    mannequin.editMeta(MannequinMeta.class, meta -> meta.setProfile(PaperProfiles.fromBukkit(player)));
                                    mannequin.addViewer(ExoPaperPlayer.of(player));

                                    return Command.SINGLE_SUCCESS;
                                }))
                            .build()
            );
        });
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ExoPaper.destroy();
    }
}
