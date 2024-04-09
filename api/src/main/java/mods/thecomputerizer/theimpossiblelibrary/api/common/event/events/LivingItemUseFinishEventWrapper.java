package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonLivingStackUseEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemStackAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_FINISH;

public abstract class LivingItemUseFinishEventWrapper<E> extends CommonLivingStackUseEventType<E> {

    protected EventFieldWrapper<E,ItemStackAPI<?>> result;

    protected LivingItemUseFinishEventWrapper() {
        super(LIVING_ITEM_USE_FINISH);
    }

    public ItemStackAPI<?> getResult() {
        return this.result.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.result = wrapResult();
    }

    public void setResult(ItemStackAPI<?> result) {
        this.result.set(this.event,result);
    }

    protected abstract EventFieldWrapper<E,ItemStackAPI<?>> wrapResult();
}