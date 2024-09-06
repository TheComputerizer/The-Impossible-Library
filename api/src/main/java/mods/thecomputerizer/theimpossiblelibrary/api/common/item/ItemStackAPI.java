package mods.thecomputerizer.theimpossiblelibrary.api.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;

import javax.annotation.Nullable;
import java.util.Objects;

public abstract class ItemStackAPI<S> extends AbstractWrapped<S> {

    protected ItemStackAPI(S stack) {
        super(stack);
    }

    @IndirectCallers
    public void decrement() {
        int count = getCount();
        if(count>0) setCount(count-1);
    }

    public abstract int getCount();
    public abstract ItemAPI<?> getItem();
    
    @IndirectCallers
    public CompoundTagAPI<?> getOrCreateTag() {
        CompoundTagAPI<?> tag = getTag();
        if(Objects.isNull(tag)) {
            tag = TagHelper.makeCompoundTag();
            setTag(tag);
        }
        return tag;
    }

    public abstract @Nullable CompoundTagAPI<?> getTag();
    
    @IndirectCallers
    public void increment() {
        setCount(getCount()+1);
    }

    public abstract boolean isEmpty();

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public abstract void setCount(int count);
    public abstract void setTag(@Nullable CompoundTagAPI<?> tag);
}