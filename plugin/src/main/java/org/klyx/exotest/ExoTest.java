package org.klyx.exotest;

import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.klyx.exo.Exo;
import org.klyx.exo.entities.BaseEntity;

public class ExoTest extends JavaPlugin {

    @Override
    public void onEnable() {
        Exo.init(this);

        Commands.literal("testspawn")
                .then(Commands.literal("zombie"))
                    .executes(ctx -> {
                        BaseEntity entity = new BaseEntity(EntityType.ZOMBIE);
                        entity.spawn(ctx.getSource().getLocation());
                        entity.addViewer((Player) ctx.getSource());

                        return Command.SINGLE_SUCCESS;
                    });
    }
}
