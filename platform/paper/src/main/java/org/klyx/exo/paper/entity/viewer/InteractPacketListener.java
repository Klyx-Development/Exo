package org.klyx.exo.paper.entity.viewer;

import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.InteractionHand;
import org.klyx.exo.entity.events.EntityInteractEvent;
import org.klyx.exo.paper.player.ExoPaperPlayer;
import org.klyx.exo.paper.util.PaperLocUtil;
import org.klyx.exo.paper.packet.impl.Packets;
import org.klyx.exo.paper.packet.impl.listener.PacketHandler;
import org.klyx.exo.paper.packet.impl.listener.PacketListener;

public class InteractPacketListener implements PacketListener<ServerboundInteractPacket> {

    public InteractPacketListener() {
        Packets.INSTANCE.registerListener(this);
    }

    @PacketHandler
    public @Nullable ServerboundInteractPacket processPacket(Player player, ServerboundInteractPacket packet) {
        int targetId = packet.entityId();

        ExoEntity target = Exo.entityManager().getEntity(targetId);
        if (target == null) return packet;

        InteractionHand hand = packet.hand() == net.minecraft.world.InteractionHand.MAIN_HAND
                ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;

        target.eventBus().post(new EntityInteractEvent(
                ExoPaperPlayer.of(player), hand,
                PaperLocUtil.toExoVec3d(packet.location()),
                packet.usingSecondaryAction()
        ));
        return null;
    }
}
