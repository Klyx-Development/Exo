package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.ExoVillagerData;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class VillagerMeta extends AbstractVillagerMeta {

    private static final MetaAccessor<ExoVillagerData> VILLAGER_DATA =
            new MetaAccessor<>(19, MetaType.VILLAGER_DATA, ExoVillagerData.DEFAULT);
    private static final MetaAccessor<Boolean> VILLAGER_FINALIZED = new MetaAccessor<>(20, MetaType.BOOLEAN, false);

    public VillagerMeta setVillagerData(ExoVillagerData data) {
        set(VILLAGER_DATA, data);
        return this;
    }

    public ExoVillagerData getVillagerData() {
        return get(VILLAGER_DATA);
    }

    public VillagerMeta setVillagerFinalized(boolean finalized) {
        set(VILLAGER_FINALIZED, finalized);
        return this;
    }

    public boolean isVillagerFinalized() {
        return get(VILLAGER_FINALIZED);
    }
}
