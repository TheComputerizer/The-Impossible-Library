package mods.thecomputerizer.theimpossiblelibrary.api.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;


public abstract class DiscBuilderAPI extends ItemBuilderAPI {
    
    protected Function<ItemStackAPI<?>,TextAPI<?>> nameSupplier;
    protected SoundEventAPI<?> sound;
    
    protected DiscBuilderAPI(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public DiscBuilderAPI addProperty(ResourceLocationAPI<?> key, BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float> propertyGetter) {
        this.propertyMap.put(key,propertyGetter);
        return this;
    }
    
    @Override public DiscBuilderAPI setCreativeTab(CreativeTabAPI tab) {
        this.creativeTab = tab;
        return this;
    }
    
    public DiscBuilderAPI setItemType(ItemType type) {
        this.itemType = type;
        return this;
    }
    
    @Override public DiscBuilderAPI setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
    
    @IndirectCallers
    public DiscBuilderAPI setSoundEvent(SoundEventAPI<?> sound) {
        this.sound = sound;
        return this;
    }
    
    @IndirectCallers
    public DiscBuilderAPI setSoundNameSupplier(Function<ItemStackAPI<?>,TextAPI<?>> name) {
        this.nameSupplier = name;
        return this;
    }
    
    public DiscBuilderAPI setStackSize(int size) {
        this.stackSize = Math.max(1,size);
        return this;
    }
    
    public DiscBuilderAPI setTooltipFunction(BiFunction<ItemStackAPI<?>,WorldAPI<?>,Collection<TextAPI<?>>> descFunc) {
        this.descFunc = descFunc;
        return this;
    }
    
    public DiscBuilderAPI setUseFunc(Function<TILItemUseContext,ActionResult> func) {
        this.useFunc = func;
        return this;
    }
}
