package org.klyx.exo.paper.meta;

import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.klyx.exo.entity.meta.ExoBlockState;
import org.klyx.exo.util.Key;

import java.util.LinkedHashMap;
import java.util.Map;

public final class PaperBlockStates {

    public static BlockState toNms(ExoBlockState state) {
        BlockData data = Bukkit.createBlockData(toBracketString(state));
        return ((CraftBlockData) data).getState();
    }

    public static ExoBlockState fromNms(BlockState state) {
        BlockData data = CraftBlockData.createData(state);
        return fromBracketString(data.getAsString());
    }

    private static String toBracketString(ExoBlockState state) {
        if (state.properties().isEmpty()) return state.block().asString();
        StringBuilder builder = new StringBuilder(state.block().asString()).append('[');

        boolean first = true;
        for (Map.Entry<String, String> entry : state.properties().entrySet()) {
            if (!first) builder.append(',');
            builder.append(entry.getKey()).append('=').append(entry.getValue());
            first = false;
        }

        return builder.append(']').toString();
    }

    private static ExoBlockState fromBracketString(String value) {
        int bracket = value.indexOf('[');
        if (bracket == -1) return new ExoBlockState(parseKey(value), Map.of());

        Map<String, String> properties = new LinkedHashMap<>();
        String propertyList = value.substring(bracket + 1, value.length() - 1);
        if (!propertyList.isEmpty()) {
            for (String pair : propertyList.split(",")) {
                int eq = pair.indexOf('=');
                properties.put(pair.substring(0, eq), pair.substring(eq + 1));
            }
        }

        return new ExoBlockState(parseKey(value.substring(0, bracket)), properties);
    }

    private static Key parseKey(String value) {
        int colon = value.indexOf(':');
        return colon == -1 ? Key.of(value) : Key.of(value.substring(0, colon), value.substring(colon + 1));
    }
}
