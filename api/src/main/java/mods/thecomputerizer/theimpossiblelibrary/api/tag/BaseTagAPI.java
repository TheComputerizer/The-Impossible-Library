package mods.thecomputerizer.theimpossiblelibrary.api.tag;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

/**
 * Stores types for base, list, and compound tags
 */
@SuppressWarnings("unused")
public abstract class BaseTagAPI<T> extends AbstractWrapped<T> {
    
    protected BaseTagAPI(T tag) {
        super(tag);
    }
    
    public abstract CompoundTagAPI<?> asCompoundTag();
    public abstract ListTagAPI<?> asListTag();
    public abstract PrimitiveTagAPI<?> asPrimitiveTag();
    public abstract StringTagAPI<?> asStringTag();
    public abstract boolean isCompound();
    public abstract boolean isList();
    public abstract boolean isPrimitive();
    public abstract boolean isString();
    
    public void write(ByteBuf buf) {
        if(isCompound()) NetworkHelper.writeTag(buf,(CompoundTagAPI<?>)this);
    }
}