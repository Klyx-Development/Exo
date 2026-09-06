package org.klyx.exotest;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.components.types.AttackComponent;
import org.klyx.exo.paper.entity.components.types.tick.LookAtComponent;
import org.klyx.exo.entity.data.EntityData;
import org.klyx.exo.entity.meta.types.entity.living.avatar.MannequinMeta;
import org.klyx.exo.paper.entity.PaperEntityTypes;
import org.klyx.exo.paper.player.ExoPaperPlayer;

public class TestMannequin extends ExoEntity {

    @Override
    public EntityData.Builder define() {
        return EntityData.builder()
                .entityType(PaperEntityTypes.toExo(EntityType.MANNEQUIN))
                .components(new LookAtComponent(), new AttackComponent(event ->
                        ((ExoPaperPlayer) event.attacker()).bukkit().sendMessage(Component.text("How could you?"))))
                .meta(MannequinMeta.class, meta -> {
                    meta.setImmovable(true);
                });
    }

}
