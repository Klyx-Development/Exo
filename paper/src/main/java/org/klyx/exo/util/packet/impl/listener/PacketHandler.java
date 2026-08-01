package org.klyx.exo.util.packet.impl.listener;


import org.klyx.exo.util.packet.impl.Packets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When a {@link PacketListener} is registered via PacketApi.registerListener, all of its functions
 * annotated with [PacketHandler] are registered as handlers for the respective packet guiType.
 * <p>
 * The priority of the packet handler being run. Handlers with a lower priority are
 * run before those with a higher priority. This value should not be negative.
 *
 * @see PacketListener
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketHandler {
    int priority() default Packets.DEFAULT_HANDLER_PRIORITY;
}

