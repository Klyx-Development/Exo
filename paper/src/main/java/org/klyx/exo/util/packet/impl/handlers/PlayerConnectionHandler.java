package org.klyx.exo.util.packet.impl.handlers;

import net.minecraft.network.Connection;
import org.klyx.exo.Exo;
import org.klyx.exo.util.packet.impl.Packets;

import java.util.NoSuchElementException;

public class PlayerConnectionHandler {

    private final String key;
    private final Packets packets;
    private final Connection connection;

    private static final String DEFAULT_MINECRAFT_HANDLER_KEY = "packet_handler";

    private boolean registered = false;

    public PlayerConnectionHandler(String key, Packets packets, Connection connection) {
        this.key = key;
        this.packets = packets;
        this.connection = connection;
    }

    public void register() {
        if (registered) return;
        registered = true;
        connection.channel.pipeline().addBefore(
                DEFAULT_MINECRAFT_HANDLER_KEY,
                key,
                new ChannelPacketHandler(packets, connection)
        );
    }

    /**
     * @param disconnect should be true on unregistering the player on quit.
     */
    public void unregister(boolean disconnect) {
        if (!registered) return;
        registered = false;

        if (!disconnect) {
            try {
                connection.channel.pipeline().remove(key);
            } catch (NoSuchElementException e) {
                Exo.logger().error("An unexpected error occurred while trying to remove a packet handler from a player.", e);
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
