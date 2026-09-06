package org.klyx.exo.entity.data.attribute;

import java.util.List;

public record ExoAttributeSnapshot(ExoAttribute attribute, double base, List<ExoAttributeModifier> modifiers) {
}
