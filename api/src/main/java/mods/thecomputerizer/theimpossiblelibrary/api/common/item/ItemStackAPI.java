package mods.thecomputerizer.theimpossiblelibrary.api.common.item;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;

import javax.annotation.Nullable;
import java.util.Objects;

@Getter
public abstract class ItemStackAPI<S> {

    protected final S stack;

    protected ItemStackAPI(S stack) {
        this.stack = stack;
    }

    public abstract ItemAPI<?> getItem();

    public CompoundTagAPI getOrCreateTag() {
        CompoundTagAPI tag = getTag();
        if(Objects.isNull(tag)) {
            tag = TagHelper.makeCompoundTag();
            setTag(tag);
        }
        return tag;
    }

    public abstract @Nullable CompoundTagAPI getTag();
    public abstract void setTag(@Nullable CompoundTagAPI tag);
}