package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_LOOTING_LEVEL;

public abstract class LootingLevelEventWrapper<E> extends CommonEventWrapper<E> {

    protected LootingLevelEventWrapper() {
        super(LIVING_LOOTING_LEVEL);
    }

    @Override
    protected void populate() {

    }
}