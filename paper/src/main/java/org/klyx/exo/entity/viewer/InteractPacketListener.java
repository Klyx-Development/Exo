package org.klyx.exo.entity.viewer;

import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.events.EntityInteractEvent;
import org.klyx.exo.util.packet.impl.Packets;
import org.klyx.exo.util.packet.impl.listener.PacketHandler;
import org.klyx.exo.util.packet.impl.listener.PacketListener;

public class InteractPacketListener implements PacketListener<ServerboundInteractPacket> {

    public InteractPacketListener() {
        Packets.INSTANCE.registerListener(this);
    }

    @PacketHandler
    public @Nullable ServerboundInteractPacket processPacket(Player player, ServerboundInteractPacket packet) {
        int targetId = packet.entityId();

        ExoEntity target = Exo.entityManager().getEntity(targetId);
        if (target == null) return packet;

        target.eventBus().post(new EntityInteractEvent(player, packet.hand(), packet.location(), packet.usingSecondaryAction()));
        return null;
    }
}
