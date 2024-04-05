package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_POTENTIAL_SPAWNS;

public abstract class PotentialSpawnsEventWrapper<E> extends CommonEventWrapper<E> {

    protected PotentialSpawnsEventWrapper() {
        super(WORLD_POTENTIAL_SPAWNS);
    }

    @Override
    protected void populate() {

    }
}