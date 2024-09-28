package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonBlockStatePlayerEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;

import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_HARVEST;

public abstract class HarvestBlockDropsEventWrapper<E> extends CommonBlockStatePlayerEventType<E> {

    protected EventFieldWrapper<E,List<ItemStackAPI<?>>> drops;
    protected EventFieldWrapper<E,Float> dropChance;
    protected EventFieldWrapper<E,Integer> fortuneLevel;
    protected EventFieldWrapper<E,Boolean> silkTouching;

    protected HarvestBlockDropsEventWrapper() {
        super(BLOCK_HARVEST);
    }

    public List<ItemStackAPI<?>> getDrops() {
        return this.drops.get(this.event);
    }

    public float getDropChance() {
        return this.dropChance.get(this.event);
    }

    public int getFortuneLevel() {
        return this.fortuneLevel.get(this.event);
    }

    public boolean isSilkTouching() {
        return this.silkTouching.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.drops = wrapDropsField();
        this.dropChance = wrapDropChanceField();
        this.fortuneLevel = wrapFortuneLevelField();
        this.silkTouching = wrapSilkTouchingField();
    }

    public void setDropChance(float chance) {
        this.dropChance.set(this.event,chance);
    }

    protected abstract EventFieldWrapper<E,List<ItemStackAPI<?>>> wrapDropsField();
    protected abstract EventFieldWrapper<E,Float> wrapDropChanceField();
    protected abstract EventFieldWrapper<E,Integer> wrapFortuneLevelField();
    protected abstract EventFieldWrapper<E,Boolean> wrapSilkTouchingField();
}