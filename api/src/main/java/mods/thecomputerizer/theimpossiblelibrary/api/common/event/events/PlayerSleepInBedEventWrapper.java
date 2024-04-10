package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_SLEEP_IN_BED;

public abstract class PlayerSleepInBedEventWrapper<E> extends CommonPlayerEventType<E> { //TODO Figure out the SleepResult field

    protected EventFieldWrapper<E,BlockPosAPI<?>> pos;

    protected PlayerSleepInBedEventWrapper() {
        super(PLAYER_SLEEP_IN_BED);
    }

    public BlockPosAPI<?> getPos() {
        return this.pos.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.pos = wrapPosField();
    }

    protected abstract EventFieldWrapper<E,BlockPosAPI<?>> wrapPosField();
}