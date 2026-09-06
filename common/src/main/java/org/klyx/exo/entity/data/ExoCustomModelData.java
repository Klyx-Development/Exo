package org.klyx.exo.entity.data;

import java.util.List;

public record ExoCustomModelData(List<Float> floats, List<Boolean> flags, List<String> strings, List<Integer> colors) { }
