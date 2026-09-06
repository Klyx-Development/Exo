package org.klyx.exo.minestom.meta;

import net.minestom.server.instance.block.Block;
import org.klyx.exo.entity.meta.ExoBlockState;
import org.klyx.exo.util.Key;

public final class MinestomBlockStates {

    public static Block toMinestom(ExoBlockState state) {
        Block block = Block.fromKey(state.block());
        if (block == null) throw new IllegalArgumentException("Unknown block: " + state.block().asString());
        return state.properties().isEmpty() ? block : block.withProperties(state.properties());
    }

    public static ExoBlockState fromMinestom(Block block) {
        return new ExoBlockState(Key.of(block.key().namespace(), block.key().value()), block.properties());
    }
}
