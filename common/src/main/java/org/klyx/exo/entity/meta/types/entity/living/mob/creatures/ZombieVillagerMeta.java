package org.klyx.exo.entity.meta.types.entity.living.mob.creatures;

import org.klyx.exo.entity.meta.ExoVillagerData;
import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class ZombieVillagerMeta extends ZombieMeta {

    private static final MetaAccessor<Boolean> CONVERTING = new MetaAccessor<>(19, MetaType.BOOLEAN, false);
    private static final MetaAccessor<ExoVillagerData> VILLAGER_DATA =
            new MetaAccessor<>(20, MetaType.VILLAGER_DATA, ExoVillagerData.DEFAULT);
    private static final MetaAccessor<Boolean> VILLAGER_FINALIZED = new MetaAccessor<>(21, MetaType.BOOLEAN, false);

    public ZombieVillagerMeta setConverting(boolean converting) {
        set(CONVERTING, converting);
        return this;
    }

    public boolean isConverting() {
        return get(CONVERTING);
    }

    public ZombieVillagerMeta setVillagerData(ExoVillagerData data) {
        set(VILLAGER_DATA, data);
        return this;
    }

    public ExoVillagerData getVillagerData() {
        return get(VILLAGER_DATA);
    }

    public ZombieVillagerMeta setVillagerFinalized(boolean finalized) {
        set(VILLAGER_FINALIZED, finalized);
        return this;
    }

    public boolean isVillagerFinalized() {
        return get(VILLAGER_FINALIZED);
    }
}
