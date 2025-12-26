package org.klyx.exo.entities.impl.components;

import net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.klyx.exo.data.entity.EntityState;
import org.klyx.exo.entities.impl.AbstractEntity;
import org.klyx.exo.utils.PacketUtil;

public class SpatialComponent {
    private final AbstractEntity entity;
    private Location location;

    public SpatialComponent(AbstractEntity entity) {
        this.entity = entity;
    }

    public void updateLocation(@NotNull Location location) {
        this.location = location;
    }

    public void teleportTo(@NotNull Location location) {
        if (entity.getState() != EntityState.ALIVE) return;
        if (entity.getVehicle() != null) entity.

        updateLocation(location);

        ClientboundEntityPositionSyncPacket packet = new ClientboundEntityPositionSyncPacket(
                entity.getEntityId(),
                new PositionMoveRotation(
                        new Vec3(location.getX(), location.getY(), location.getZ()),
                        Vec3.ZERO,
                        location.getYaw(),
                        location.getPitch()
                ),
                true
        );

        PacketUtil.sendPacket(entity.getViewers(), packet);
    }

    public @Nullable Location getLocation() {
        return location != null ? location.clone() : null;
    }

}
