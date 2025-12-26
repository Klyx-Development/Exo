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
import org.bukkit.util.Vector;
import org.klyx.exo.Exo;
import org.klyx.exo.data.metadata.EntityFlags;
import org.klyx.exo.entities.base.BaseLivingEntity;
import org.klyx.exo.entities.specific.entity.PacketAreaEffectCloud;
import org.klyx.exo.entities.specific.entity.displays.PacketBlockDisplay;
import org.klyx.exo.entities.specific.entity.displays.PacketItemDisplay;
import org.klyx.exo.entities.specific.livingentity.PacketArmorStand;
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
                            .then(Commands.literal("zombie")
                                .executes(ctx -> {
//                                    BaseLivingEntity entity = new BaseLivingEntity(EntityType.ZOMBIE);
//                                    entity.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
//                                    entity.spawn(ctx.getSource().getLocation());
//                                    entity.showTo((Player) ctx.getSource().getSender());

                                    PacketArmorStand stand = new PacketArmorStand();
                                    stand.spawn(ctx.getSource().getLocation());
                                    stand.showTo((Player) ctx.getSource().getSender());

                                    stand.addPassenger(((Player) ctx.getSource().getSender()).getEntityId());

                                    return Command.SINGLE_SUCCESS;
                                }))
                            .then(Commands.literal("specific")
                                .executes(ctx -> {
                                    PacketAreaEffectCloud cloud = new PacketAreaEffectCloud();
                                    //cloud.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                                    cloud.spawn(ctx.getSource().getLocation());
                                    cloud.onViewerAdded(player -> {
                                        Bukkit.broadcast(Component.text("woah"));
                                    });
                                    cloud.showTo((Player) ctx.getSource().getSender());

                                    return Command.SINGLE_SUCCESS;
                                }))
                            .then(Commands.literal("display")
                                .executes(ctx -> {
                                    PacketBlockDisplay display = new PacketBlockDisplay();
                                    display.setBlock(Bukkit.createBlockData(Material.DIAMOND_BLOCK));

                                    display.spawn(ctx.getSource().getLocation());
                                    display.showTo((Player) ctx.getSource().getSender());

                                    display.setScale(new Vector(0, 0, 0));
                                    display.setInterpolationDuration(10);
                                    display.setInterpolationDelay(-1);

                                    Bukkit.getScheduler().runTaskLater(this, () -> {
                                        display.setTranslation(new Vector(-0.5, -0.5, -0.5));
                                        display.setScale(new Vector(1, 1, 1));
                                        Bukkit.getScheduler().runTaskLater(this, display::despawn, 10L);
                                    }, 1L);

                                    return Command.SINGLE_SUCCESS;
                                }))
                            .then(Commands.literal("flagstest")
                                    .executes(ctx -> {
                                        BaseLivingEntity zombie = new BaseLivingEntity(EntityType.ZOMBIE);
                                        zombie.setOnFire(true);
                                        zombie.setGlowing(true);

                                        zombie.spawn(ctx.getSource().getLocation());

                                        VisibilityBuilder.forEntity(zombie)
                                                .global()
                                                .sameWorld()
                                                .apply();

                                        return Command.SINGLE_SUCCESS;
                                    }))

                            .build()
            );
        });
    }
}
