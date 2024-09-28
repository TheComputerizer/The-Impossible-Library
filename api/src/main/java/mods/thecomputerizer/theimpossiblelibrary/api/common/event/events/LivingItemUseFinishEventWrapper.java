package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingStackUseEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_FINISH;

public abstract class LivingItemUseFinishEventWrapper<E> extends CommonLivingStackUseEventType<E> {

    protected EventFieldWrapper<E,ItemStackAPI<?>> stackResult;

    protected LivingItemUseFinishEventWrapper() {
        super(LIVING_ITEM_USE_FINISH);
    }

    public ItemStackAPI<?> getStackResult() {
        return this.stackResult.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.stackResult = wrapStackResultField();
    }

    public void setResult(ItemStackAPI<?> result) {
        this.stackResult.set(this.event,result);
    }

    protected abstract EventFieldWrapper<E,ItemStackAPI<?>> wrapStackResultField();
}