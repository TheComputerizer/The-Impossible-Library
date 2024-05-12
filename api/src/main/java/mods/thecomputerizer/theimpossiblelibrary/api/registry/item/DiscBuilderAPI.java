package mods.thecomputerizer.theimpossiblelibrary.api.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("unused") public abstract class DiscBuilderAPI extends ItemBuilderAPI {
    
    protected String name;
    protected SoundEventAPI<?> sound;
    
    protected DiscBuilderAPI(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override
    public DiscBuilderAPI setCreativeTab(CreativeTabAPI tab) {
        this.creativeTab = tab;
        return this;
    }
    
    public DiscBuilderAPI setItemType(ItemType type) {
        this.itemType = type;
        return this;
    }
    
    @Override
    public DiscBuilderAPI setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
    
    public DiscBuilderAPI setSoundEvent(SoundEventAPI<?> sound) {
        this.sound = sound;
        return this;
    }
    
    public DiscBuilderAPI setSoundName(String name) {
        this.name = name;
        return this;
    }
    
    public DiscBuilderAPI setStackSize(int size) {
        this.stackSize = Math.max(1,size);
        return this;
    }
    
    public DiscBuilderAPI setTootltipFunction(BiFunction<ItemStackAPI<?>,WorldAPI<?>,Collection<TextAPI<?>>> descFunc) {
        this.descFunc = descFunc;
        return this;
    }
    
    public DiscBuilderAPI setUseFunc(Function<TILItemUseContext,ActionResult> func) {
        this.useFunc = func;
        return this;
    }
}
