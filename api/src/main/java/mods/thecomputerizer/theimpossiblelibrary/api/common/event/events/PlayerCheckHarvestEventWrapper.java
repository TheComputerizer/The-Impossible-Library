package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerStateEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_CHECK_HARVEST;

public abstract class PlayerCheckHarvestEventWrapper<E> extends CommonPlayerStateEventType<E> {

    protected EventFieldWrapper<E,Boolean> success;

    protected PlayerCheckHarvestEventWrapper() {
        super(PLAYER_CHECK_HARVEST);
    }

    public boolean canHarvest() {
        return this.success.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.success = wrapSuccessField();
    }

    public void setHarvestable(boolean canHarvest) {
        this.success.set(this.event,canHarvest);
    }

    protected abstract EventFieldWrapper<E,Boolean> wrapSuccessField();
}