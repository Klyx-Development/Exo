package org.klyx.exo.paper.entity.components.types;

import net.minecraft.network.protocol.game.ClientboundDamageEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.components.EntityComponent;
import org.klyx.exo.entity.events.EntityDamageEvent;
import org.klyx.exo.paper.packet.impl.Packets;
import org.klyx.exo.paper.player.ExoPaperPlayer;
import org.klyx.exo.paper.util.PaperLocUtil;
import org.klyx.exo.player.ExoPlayer;

import java.util.Optional;
import java.util.UUID;

public class HurtAnimationComponent implements EntityComponent {

    private @Nullable ExoEntity entity;

    @Override
    public void initialize(ExoEntity entity) {
        this.entity = entity;
        entity.eventBus().on(EntityDamageEvent.class, this::handleDamage);
    }

    private void handleDamage(EntityDamageEvent event) {
        if (entity == null || !entity.isSpawned() || event.isCancelled()) return;

        World bukkitWorld = PaperLocUtil.toBukkitWorld(entity.getWorld());
        ServerLevel level = ((CraftWorld) bukkitWorld).getHandle();

        ExoPlayer attacker = event.attacker();
        DamageSource damageSource;
        int sourceId;
        if (attacker instanceof ExoPaperPlayer paperAttacker) {
            ServerPlayer nmsAttacker = ((CraftPlayer) paperAttacker.bukkit()).getHandle();
            damageSource = level.damageSources().playerAttack(nmsAttacker);
            sourceId = paperAttacker.entityId();
        } else {
            damageSource = level.damageSources().generic();
            sourceId = -1;
        }

        ClientboundDamageEventPacket packet = new ClientboundDamageEventPacket(
                entity.entityId(), damageSource.typeHolder(), sourceId, sourceId, Optional.empty()
        );

        for (UUID viewer : entity.getActiveViewers()) {
            Packets.INSTANCE.sendPacket(viewer, packet);
        }
    }
}
