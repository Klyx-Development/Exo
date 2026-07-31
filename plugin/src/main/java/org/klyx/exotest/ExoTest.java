package org.klyx.exotest;

import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.components.types.PassengerComponent;
import org.klyx.exo.entity.meta.types.entity.living.avatar.MannequinMeta;
import org.klyx.exo.entity.meta.types.entity.living.mob.creatures.ZombieMeta;

public class ExoTest extends JavaPlugin {

    @Override
    public void onEnable() {
        Exo.init(this);

        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(
                    Commands.literal("testspawn")
                            .then(Commands.literal("zombie")
                                .executes(ctx -> {
                                    TestZombie zombie = new TestZombie();
                                    zombie.spawn(ctx.getSource().getLocation());
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
                                    TestMannequin mannequin = new TestMannequin();
                                    if (!(ctx.getSource().getExecutor() instanceof Player player)) return Command.SINGLE_SUCCESS;

                                    mannequin.spawn(player.getLocation());
                                    mannequin.editMeta(MannequinMeta.class, meta -> meta.setProfile(player));
                                    mannequin.addViewer(player);

                                    this.getServer().getScheduler().runTaskLater(this, mannequin::destroy, 20 * 20);

                                    return Command.SINGLE_SUCCESS;
                                }))
                            .build()
            );
        });
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Exo.destroy();
    }
}
