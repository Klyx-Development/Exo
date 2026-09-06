package org.klyx.exo.paper.entity.viewer;

import net.minecraft.network.protocol.game.ServerboundAttackPacket;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.events.EntityAttackEvent;
import org.klyx.exo.paper.player.ExoPaperPlayer;
import org.klyx.exo.paper.packet.impl.Packets;
import org.klyx.exo.paper.packet.impl.listener.PacketHandler;
import org.klyx.exo.paper.packet.impl.listener.PacketListener;

public class AttackPacketListener implements PacketListener<ServerboundAttackPacket> {

    public AttackPacketListener() {
        Packets.INSTANCE.registerListener(this);
    }

    @PacketHandler
    public @Nullable ServerboundAttackPacket processPacket(Player player, ServerboundAttackPacket packet) {
        int targetId = packet.entityId();

        ExoEntity target = Exo.entityManager().getEntity(targetId);
        if (target == null) return packet;

        target.eventBus().post(new EntityAttackEvent(ExoPaperPlayer.of(player)));
        return null;
    }
}
