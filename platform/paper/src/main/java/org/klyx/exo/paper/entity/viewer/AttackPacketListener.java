package org.klyx.exo.paper.entity.viewer;

import net.minecraft.network.protocol.game.ServerboundAttackPacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.Exo;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.events.EntityAttackEvent;
import org.klyx.exo.paper.ExoPaper;
import org.klyx.exo.paper.packet.impl.Packets;
import org.klyx.exo.paper.packet.impl.listener.PacketHandler;
import org.klyx.exo.paper.packet.impl.listener.PacketListener;
import org.klyx.exo.paper.player.ExoPaperPlayer;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class AttackPacketListener implements PacketListener<ServerboundAttackPacket> {

    public AttackPacketListener() {
        if (hasAttackPacket()) {
            Packets.INSTANCE.registerListener(this);
        } else {
            registerLegacy();
        }
    }

    @PacketHandler
    public @Nullable ServerboundAttackPacket processPacket(Player player, ServerboundAttackPacket packet) {
        int targetId = packet.entityId();

        ExoEntity target = Exo.entityManager().getEntity(targetId);
        if (target == null) return packet;

        target.eventBus().post(new EntityAttackEvent(ExoPaperPlayer.of(player)));
        return null;
    }

    private static boolean hasAttackPacket() {
        try {
            Class.forName("net.minecraft.network.protocol.game.ServerboundAttackPacket");
            return true;
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            return false;
        }
    }

    /**
     * In 26.2, the ServerboundInteractPacket was split into 2, ServerboundAttackPacket and ServerboundInteractPacket
     * This basically just prevents a NoClassDefFoundException
     */
    private void registerLegacy() {
        try {
            Method isAttack = ServerboundInteractPacket.class.getMethod("isAttack");
            Method getEntityId = ServerboundInteractPacket.class.getMethod("getEntityId");

            Packets.INSTANCE.registerHandler(
                    ServerboundInteractPacket.class, Player.class, Packets.DEFAULT_HANDLER_PRIORITY,
                    (player, packet) -> {
                        try {
                            if (!(boolean) isAttack.invoke(packet)) return List.of(packet);

                            int targetId = (int) getEntityId.invoke(packet);
                            ExoEntity target = Exo.entityManager().getEntity(targetId);
                            if (target == null) return List.of(packet);

                            target.eventBus().post(new EntityAttackEvent(ExoPaperPlayer.of(player)));
                            return Collections.emptyList();
                        } catch (ReflectiveOperationException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        } catch (NoSuchMethodException e) {
            ExoPaper.logger().warn("The server doesn't have an interact OR attack packet, something has gone very wrong!", e);
        }
    }
}
