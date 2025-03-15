package mods.thecomputerizer.theimpossiblelibrary.api.registry.tab;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.MutableWrapped;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

/**
 * Holder specifically for creative tab registration in forge/neoforge 1.19.4+.
 * Acts as an intermediary between CreativeTabBuilderAPI & CreativeTabAPI before the required registry event is fired
 */
public abstract class FutureCreativeTab<T> extends MutableWrapped<T> {
    
    @Getter protected final ResourceLocationAPI<?> registryName;
    @Setter protected Supplier<?> iconSupplier;
    @Getter protected boolean registered;
    
    public FutureCreativeTab(ResourceLocationAPI<?> registryName) {
        super();
        this.registryName = registryName;
    }
    
    /**
     * Needed specifically for 1.19.2 where there is not a registry event
     */
    public FutureCreativeTab(T wrapped) {
        super(wrapped);
        this.registryName = null;
        this.registered = true;
    }
    
    public abstract void register(@Nullable Object arg);
    
    @Override public FutureCreativeTab<T> setWrapped(T wrapped) {
        this.wrapped = wrapped;
        return this;
    }
    
    public abstract void supply(@Nullable Object arg, List<Supplier<ItemStackAPI<?>>> stackSuppliers);
}