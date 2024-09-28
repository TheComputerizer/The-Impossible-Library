package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingDamageEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_LOOTING_LEVEL;

public abstract class LootingLevelEventWrapper<E> extends CommonLivingDamageEventType<E> {

    protected EventFieldWrapper<E,Integer> lootingLevel;

    protected LootingLevelEventWrapper() {
        super(LIVING_LOOTING_LEVEL);
    }

    public int getLootingLevel() {
        return this.lootingLevel.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.lootingLevel = wrapLootingLevelField();
    }

    public void setLootingLevel(int level) {
        this.lootingLevel.set(this.event,level);
    }

    protected abstract EventFieldWrapper<E,Integer> wrapLootingLevelField();
}