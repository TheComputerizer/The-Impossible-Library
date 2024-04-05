package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_SLEEP_IN_BED;

public abstract class PlayerSleepInBedEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerSleepInBedEventWrapper() {
        super(PLAYER_SLEEP_IN_BED);
    }

    @Override
    protected void populate() {

    }
}