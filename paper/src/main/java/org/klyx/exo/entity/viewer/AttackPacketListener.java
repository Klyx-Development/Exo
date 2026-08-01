package org.klyx.exo.entity.viewer;

import net.minecraft.network.protocol.game.ServerboundAttackPacket;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.events.EntityAttackEvent;
import org.klyx.exo.util.packet.impl.Packets;
import org.klyx.exo.util.packet.impl.listener.PacketHandler;
import org.klyx.exo.util.packet.impl.listener.PacketListener;

public class AttackPacketListener implements PacketListener<ServerboundAttackPacket> {

    public AttackPacketListener() {
        Packets.INSTANCE.registerListener(this);
    }

    @PacketHandler
    public @Nullable ServerboundAttackPacket processPacket(Player player, ServerboundAttackPacket packet) {
        int targetId = packet.entityId();

        ExoEntity target = Exo.entityManager().getEntity(targetId);
        if (target == null) return packet;

        target.eventBus().post(new EntityAttackEvent(player));
        return null;
    }
}
