package org.klyx.exotest;

import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.klyx.exo.Exo;
import org.klyx.exo.entities.base.BaseEntity;
import org.klyx.exo.entities.base.BaseLivingEntity;
import org.klyx.exo.visibility.VisibilityBuilder;

public class ExoTest extends JavaPlugin {

    @Override
    public void onEnable() {
        Exo.init(this);

        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(
                    Commands.literal("testspawn")
                            .then(Commands.literal("zombie"))
                            .executes(ctx -> {
                                BaseLivingEntity entity = new BaseLivingEntity(EntityType.ZOMBIE);
                                entity.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                                entity.spawn(ctx.getSource().getLocation());
                                entity.showTo((Player) ctx.getSource().getSender());

                                return Command.SINGLE_SUCCESS;
                            })
                            .build()
            );
        });
    }
}
