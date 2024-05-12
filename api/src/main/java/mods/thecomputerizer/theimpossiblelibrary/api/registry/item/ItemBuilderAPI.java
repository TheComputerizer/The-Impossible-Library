package mods.thecomputerizer.theimpossiblelibrary.api.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryBuilder;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI.ItemType.BASIC;

@SuppressWarnings("unused") public abstract class ItemBuilderAPI extends RegistryEntryBuilder<ItemAPI<?>> {
    
    protected CreativeTabAPI creativeTab;
    protected BiFunction<ItemStackAPI<?>,WorldAPI<?>,Collection<TextAPI<?>>> descFunc;
    protected ItemType itemType;
    protected int stackSize;
    protected Function<TILItemUseContext,ActionResult> useFunc;
    
    protected ItemBuilderAPI(@Nullable ItemBuilderAPI parent) {
        if(Objects.nonNull(parent)) {
            this.creativeTab = parent.creativeTab;
            this.descFunc = parent.descFunc;
            this.stackSize = parent.stackSize;
            this.useFunc = parent.useFunc;
        } else {
            this.creativeTab = null;
            this.descFunc = null;
            this.stackSize = 1;
            this.useFunc = null;
        }
        this.itemType = BASIC;
    }
    
    public ItemProperties buildProperties() {
        return new ItemProperties(this.creativeTab,this.stackSize,this.registryName,this.descFunc,this.useFunc);
    }
    
    public ItemBuilderAPI setCreativeTab(CreativeTabAPI tab) {
        this.creativeTab = tab;
        return this;
    }
    
    public ItemBuilderAPI setItemType(ItemType type) {
        this.itemType = type;
        return this;
    }
    
    @Override
    public ItemBuilderAPI setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
    
    public ItemBuilderAPI setStackSize(int size) {
        this.stackSize = Math.max(1,size);
        return this;
    }
    
    public ItemBuilderAPI setTootltipFunction(BiFunction<ItemStackAPI<?>,WorldAPI<?>,Collection<TextAPI<?>>> descFunc) {
        this.descFunc = descFunc;
        return this;
    }
    
    public ToolBuilderAPI setToolType(ToolType tool) {
        return RegistryHelper.makeToolBuilder(this,tool);
    }
    
    public ItemBuilderAPI setUseFunc(Function<TILItemUseContext,ActionResult> func) {
        this.useFunc = func;
        return this;
    }
    
    public enum ItemType{ BASIC, BOW, DISC, FISHING_ROD, TOOL }
    public enum ToolType { AXE, CUSTOM, HOE, PICKAXE, SHOVEL, SWORD }
}