package mods.thecomputerizer.theimpossiblelibrary.api.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
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
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI.ItemType.BLOCK;

public abstract class ItemBlockBuilderAPI extends ItemBuilderAPI {
    
    protected Supplier<BlockAPI<?>> block;
    
    protected ItemBlockBuilderAPI(@Nullable ItemBuilderAPI parent) {
        super(parent);
        this.itemType = BLOCK;
    }
    
    @Override
    public ItemBlockBuilderAPI addProperty(
            ResourceLocationAPI<?> key, BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float> properyGetter) {
        this.propertyMap.put(key,properyGetter);
        return this;
    }
    
    public ItemBlockBuilderAPI setBlock(Supplier<BlockAPI<?>> supplier) {
        this.block = supplier;
        return this;
    }
    
    public ItemBlockBuilderAPI setCreativeTab(CreativeTabAPI tab) {
        this.creativeTab = tab;
        return this;
    }
    
    public ItemBlockBuilderAPI setItemType(ItemType type) {
        this.itemType = BLOCK;
        return this;
    }
    
    @Override
    public ItemBlockBuilderAPI setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
    
    public ItemBlockBuilderAPI setStackSize(int size) {
        this.stackSize = Math.max(1,size);
        return this;
    }
    
    public ItemBlockBuilderAPI setTootltipFunction(BiFunction<ItemStackAPI<?>,WorldAPI<?>,Collection<TextAPI<?>>> descFunc) {
        this.descFunc = descFunc;
        return this;
    }
    
    public ItemBlockBuilderAPI setUseFunc(Function<TILItemUseContext,ActionResult> func) {
        this.useFunc = func;
        return this;
    }
}
