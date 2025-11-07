package org.klyx.exotest;

import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.klyx.exo.Exo;
import org.klyx.exo.entities.base.BaseLivingEntity;
import org.klyx.exo.entities.specific.entity.PacketAreaEffectCloud;
import org.klyx.exo.entities.specific.livingentity.PacketArmorStand;

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
                                    BaseLivingEntity entity = new BaseLivingEntity(EntityType.ZOMBIE);
                                    entity.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                                    entity.spawn(ctx.getSource().getLocation());
                                    entity.showTo((Player) ctx.getSource().getSender());

                                    PacketArmorStand stand = new PacketArmorStand();
                                    stand.spawn(ctx.getSource().getLocation());
                                    stand.showTo((Player) ctx.getSource().getSender());

                                    stand.addPassenger(entity.getEntityId());

                                    return Command.SINGLE_SUCCESS;
                                }))
                            .then(Commands.literal("specific")
                                .executes(ctx -> {
                                    PacketAreaEffectCloud cloud = new PacketAreaEffectCloud();
                                    //cloud.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                                    cloud.spawn(ctx.getSource().getLocation());
                                    cloud.showTo((Player) ctx.getSource().getSender());
                                    cloud.onViewerAdded(player -> {
                                        Bukkit.broadcast(Component.text("woah"));
                                    });

                                    return Command.SINGLE_SUCCESS;
                                }))

                            .build()
            );
        });
    }
}
