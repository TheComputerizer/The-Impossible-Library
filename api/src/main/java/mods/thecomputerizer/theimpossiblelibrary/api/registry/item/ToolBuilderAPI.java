package mods.thecomputerizer.theimpossiblelibrary.api.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI.ItemType.TOOL;

public abstract class ToolBuilderAPI extends ItemBuilderAPI {
    
    protected ToolType toolType;
    
    protected ToolBuilderAPI(@Nullable ItemBuilderAPI parent, ToolType type) {
        super(parent);
        this.itemType = TOOL;
        this.toolType = type;
    }
    
    @Override
    public ToolBuilderAPI addProperty(ResourceLocationAPI<?> key, BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float> properyGetter) {
        this.propertyMap.put(key,properyGetter);
        return this;
    }
    
    @Override
    public ToolBuilderAPI setCreativeTab(CreativeTabAPI tab) {
        this.creativeTab = tab;
        return this;
    }
    
    public ToolBuilderAPI setItemType(ItemType type) {
        this.itemType = type;
        return this;
    }
    
    @Override
    public ToolBuilderAPI setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
    
    public ToolBuilderAPI setStackSize(int size) {
        this.stackSize = Math.max(1,size);
        return this;
    }
    
    public ToolBuilderAPI setTootltipFunction(BiFunction<ItemStackAPI<?>,WorldAPI<?>,Collection<TextAPI<?>>> descFunc) {
        this.descFunc = descFunc;
        return this;
    }
    
    public ToolBuilderAPI setToolType(ToolType type) {
        this.toolType = type;
        return this;
    }
    
    public ToolBuilderAPI setUseFunc(Function<TILItemUseContext,ActionResult> func) {
        this.useFunc = func;
        return this;
    }
}