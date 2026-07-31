package org.klyx.exotest;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.components.types.AttackComponent;
import org.klyx.exo.entity.components.types.tick.LookAtComponent;
import org.klyx.exo.entity.data.EntityData;
import org.klyx.exo.entity.meta.types.entity.living.avatar.MannequinMeta;

public class TestMannequin extends ExoEntity {

    @Override
    public EntityData.Builder define() {
        return EntityData.builder()
                .entityType(EntityType.MANNEQUIN)
                .components(new LookAtComponent(), new AttackComponent(event -> event.attacker().sendMessage(Component.text("How could you?"))))
                .meta(MannequinMeta.class, meta -> {
                    meta.setImmovable(true);
                });
    }

}
