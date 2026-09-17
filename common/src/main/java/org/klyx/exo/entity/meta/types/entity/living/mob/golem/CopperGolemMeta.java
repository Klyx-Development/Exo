package org.klyx.exo.entity.meta.types.entity.living.mob.golem;

import org.klyx.exo.entity.meta.CopperGolemState;
import org.klyx.exo.entity.meta.WeatheringCopperState;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class CopperGolemMeta extends AbstractGolemMeta {

    private static final MetaAccessor<WeatheringCopperState> WEATHER_STATE =
            new MetaAccessor<>(16, MetaType.WEATHERING_COPPER_STATE, WeatheringCopperState.UNAFFECTED);
    private static final MetaAccessor<CopperGolemState> STATE =
            new MetaAccessor<>(17, MetaType.COPPER_GOLEM_STATE, CopperGolemState.IDLE);

    public CopperGolemMeta setWeatherState(WeatheringCopperState state) {
        set(WEATHER_STATE, state);
        return this;
    }

    public WeatheringCopperState getWeatherState() {
        return get(WEATHER_STATE);
    }

    public CopperGolemMeta setState(CopperGolemState state) {
        set(STATE, state);
        return this;
    }

    public CopperGolemState getState() {
        return get(STATE);
    }
}
