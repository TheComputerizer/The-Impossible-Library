package mods.thecomputerizer.theimpossiblelibrary.legacy.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;

public abstract class BaseTagLegacy implements BaseTagAPI {

    @Override
    public CompoundTagAPI asCompundTag() {
        return null;
    }

    @Override
    public ListTagAPI asListTag() {
        return null;
    }

    @Override
    public boolean isCompound() {
        return this instanceof CompoundTagLegacy;
    }

    @Override
    public boolean isList() {
        return this instanceof ListTagLegacy;
    }

    @Override
    public boolean isString() {
        return this instanceof StringTagLegacy;
    }

    @Override
    public boolean isPrimitive() {
        return this instanceof PrimitiveTagLegacy;
    }
}
