package org.klyx.exo.paper.dispatch;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jspecify.annotations.Nullable;
import org.klyx.exo.entity.ExoEntity;
import org.klyx.exo.entity.data.attribute.ExoAttribute;
import org.klyx.exo.entity.data.attribute.ExoAttributeModifier;
import org.klyx.exo.entity.data.attribute.ExoAttributeSnapshot;
import org.klyx.exo.entity.dispatch.PacketCategory;
import org.klyx.exo.entity.dispatch.PacketDispatcher;
import org.klyx.exo.common.AttributeDispatcher;
import org.klyx.exo.util.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class PaperAttributeDispatcher implements AttributeDispatcher {

    private final PacketDispatcher<Packet<?>> packetDispatcher;

    public PaperAttributeDispatcher(PacketDispatcher<Packet<?>> packetDispatcher) {
        this.packetDispatcher = packetDispatcher;
    }

    @Override
    public void dispatchDirty(ExoEntity entity, List<ExoAttributeSnapshot> dirtySnapshots) {
        if (dirtySnapshots.isEmpty()) return;

        List<AttributeInstance> instances = new ArrayList<>(dirtySnapshots.size());
        for (ExoAttributeSnapshot snapshot : dirtySnapshots) {
            Holder<Attribute> attribute = resolveAttribute(snapshot.attribute());
            if (attribute == null) continue;

            AttributeInstance instance = new AttributeInstance(attribute, ai -> {
            });
            instance.setBaseValue(snapshot.base());
            for (ExoAttributeModifier modifier : snapshot.modifiers()) {
                instance.addTransientModifier(toNmsModifier(modifier));
            }
            instances.add(instance);
        }

        if (instances.isEmpty()) return;
        ClientboundUpdateAttributesPacket packet = new ClientboundUpdateAttributesPacket(entity.entityId(), instances);
        for (UUID viewer : entity.getActiveViewers()) {
            packetDispatcher.schedule(viewer, PacketCategory.DEFAULT, List.of(packet));
        }
    }

    private static @Nullable Holder<Attribute> resolveAttribute(ExoAttribute attribute) {
        return BuiltInRegistries.ATTRIBUTE.get(toIdentifier(attribute.key())).map(h -> (Holder<Attribute>) h).orElse(null);
    }

    private static AttributeModifier toNmsModifier(ExoAttributeModifier modifier) {
        return new AttributeModifier(toIdentifier(modifier.key()), modifier.amount(), toNmsOperation(modifier.operation()));
    }

    private static Identifier toIdentifier(Key key) {
        return Identifier.fromNamespaceAndPath(key.namespace(), key.value());
    }

    private static AttributeModifier.Operation toNmsOperation(ExoAttributeModifier.Operation operation) {
        return AttributeModifier.Operation.valueOf(operation.name());
    }
}
