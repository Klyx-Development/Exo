package org.klyx.exo.minestom.dispatch;

import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.entity.attribute.AttributeModifier;
import net.minestom.server.entity.attribute.AttributeOperation;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.play.EntityAttributesPacket;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.data.attribute.ExoAttribute;
import org.klyx.exo.entity.data.attribute.ExoAttributeModifier;
import org.klyx.exo.entity.data.attribute.ExoAttributeSnapshot;
import org.klyx.exo.entity.dispatch.PacketCategory;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.common.AttributeDispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class MinestomAttributeDispatcher implements AttributeDispatcher {

    private final PacketDispatcher<ServerPacket> packetDispatcher;

    public MinestomAttributeDispatcher(PacketDispatcher<ServerPacket> packetDispatcher) {
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public void dispatchDirty(ExoEntity entity, List<ExoAttributeSnapshot> dirtySnapshots) {
        if (dirtySnapshots.isEmpty()) return;

        List<EntityAttributesPacket.Property> properties = new ArrayList<>(dirtySnapshots.size());
        for (ExoAttributeSnapshot snapshot : dirtySnapshots) {
            Attribute attribute = resolveAttribute(snapshot.attribute());
            if (attribute == null) continue;

            List<AttributeModifier> modifiers = new ArrayList<>(snapshot.modifiers().size());
            for (ExoAttributeModifier modifier : snapshot.modifiers()) {
                modifiers.add(toMinestomModifier(modifier));
            }
            properties.add(new EntityAttributesPacket.Property(attribute, snapshot.base(), modifiers));
        }

        if (properties.isEmpty()) return;
        EntityAttributesPacket packet = new EntityAttributesPacket(entity.entityId(), properties);
        for (UUID viewer : entity.getActiveViewers()) {
            packetDispatcher.schedule(viewer, PacketCategory.DEFAULT, List.of(packet));
        }
    }

    private static @Nullable Attribute resolveAttribute(ExoAttribute attribute) {
        return Attribute.fromKey(attribute.key());
    }

    private static AttributeModifier toMinestomModifier(ExoAttributeModifier modifier) {
        return new AttributeModifier(modifier.key(), modifier.amount(), toMinestomOperation(modifier.operation()));
    }

    private static AttributeOperation toMinestomOperation(ExoAttributeModifier.Operation operation) {
        return AttributeOperation.valueOf(operation.name());
    }
}
