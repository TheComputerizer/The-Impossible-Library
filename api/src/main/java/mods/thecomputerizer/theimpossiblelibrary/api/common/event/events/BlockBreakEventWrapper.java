package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonBlockStatePlayerEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_BREAK;

public abstract class BlockBreakEventWrapper<E> extends CommonBlockStatePlayerEventType<E> {

    protected EventFieldWrapper<E,Integer> xp;

    protected BlockBreakEventWrapper() {
        super(BLOCK_BREAK);
    }

    public int getXP() {
        return this.xp.get(this.event);
    }

    @Override
    protected void populate() {
        super.populate();
        this.xp = wrapXPField();
    }

    public void setXP(int xp) {
        this.xp.set(this.event,xp);
    }

    protected abstract EventFieldWrapper<E,Integer> wrapXPField();
}