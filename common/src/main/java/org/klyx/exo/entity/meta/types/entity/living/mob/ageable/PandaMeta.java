package org.klyx.exo.entity.meta.types.entity.living.mob.ageable;

import org.klyx.exo.entity.meta.impl.MetaAccessor;
import org.klyx.exo.entity.meta.impl.MetaType;

public final class PandaMeta extends AnimalMeta {

    private static final MetaAccessor<Integer> BREED_TIMER = new MetaAccessor<>(18, MetaType.INT, 0);
    private static final MetaAccessor<Integer> SNEEZE_TIMER = new MetaAccessor<>(19, MetaType.INT, 0);
    private static final MetaAccessor<Integer> EAT_TIMER = new MetaAccessor<>(20, MetaType.INT, 0);
    private static final MetaAccessor<Byte> MAIN_GENE = new MetaAccessor<>(21, MetaType.BYTE, (byte) 0);
    private static final MetaAccessor<Byte> HIDDEN_GENE = new MetaAccessor<>(22, MetaType.BYTE, (byte) 0);
    private static final MetaAccessor<Byte> FLAGS = new MetaAccessor<>(23, MetaType.BYTE, (byte) 0);

    public PandaMeta setBreedTimer(int ticks) {
        set(BREED_TIMER, ticks);
        return this;
    }

    public int getBreedTimer() {
        return get(BREED_TIMER);
    }

    public PandaMeta setSneezeTimer(int ticks) {
        set(SNEEZE_TIMER, ticks);
        return this;
    }

    public int getSneezeTimer() {
        return get(SNEEZE_TIMER);
    }

    public PandaMeta setEatTimer(int ticks) {
        set(EAT_TIMER, ticks);
        return this;
    }

    public int getEatTimer() {
        return get(EAT_TIMER);
    }

    public PandaMeta setMainGene(byte gene) {
        set(MAIN_GENE, gene);
        return this;
    }

    public byte getMainGene() {
        return get(MAIN_GENE);
    }

    public PandaMeta setHiddenGene(byte gene) {
        set(HIDDEN_GENE, gene);
        return this;
    }

    public byte getHiddenGene() {
        return get(HIDDEN_GENE);
    }

    public PandaMeta setSneezing(boolean sneezing) {
        setFlag(FLAGS, 1, sneezing);
        return this;
    }

    public boolean isSneezing() {
        return getFlag(FLAGS, 1);
    }

    public PandaMeta setRolling(boolean rolling) {
        setFlag(FLAGS, 2, rolling);
        return this;
    }

    public boolean isRolling() {
        return getFlag(FLAGS, 2);
    }

    public PandaMeta setSitting(boolean sitting) {
        setFlag(FLAGS, 3, sitting);
        return this;
    }

    public boolean isSitting() {
        return getFlag(FLAGS, 3);
    }

    public PandaMeta setOnBack(boolean onBack) {
        setFlag(FLAGS, 4, onBack);
        return this;
    }

    public boolean isOnBack() {
        return getFlag(FLAGS, 4);
    }
}
