package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_XP_CHANGE;

public abstract class PlayerChangeXPEventWrapper<E> extends CommonPlayerEventType<E> {

    protected EventFieldWrapper<E,Integer> amount;

    protected PlayerChangeXPEventWrapper() {
        super(PLAYER_XP_CHANGE);
    }

    public int getAmount() {
        return this.amount.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.amount = wrapAmountField();
    }

    public void setAmount(int amount) {
        this.amount.set(this.event,amount);
    }

    protected abstract EventFieldWrapper<E,Integer> wrapAmountField();
}