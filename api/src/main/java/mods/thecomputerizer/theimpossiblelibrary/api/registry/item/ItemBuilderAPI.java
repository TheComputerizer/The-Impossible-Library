package mods.thecomputerizer.theimpossiblelibrary.api.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryBuilder;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI.ItemType.BASIC;

public abstract class ItemBuilderAPI extends RegistryEntryBuilder<ItemAPI<?>> {
    
    protected CreativeTabAPI creativeTab;
    protected BiFunction<ItemStackAPI<?>,WorldAPI<?>,Collection<TextAPI<?>>> descFunc;
    protected ItemType itemType;
    protected Map<ResourceLocationAPI<?>,BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float>> propertyMap;
    protected int stackSize;
    protected Function<TILItemUseContext,ActionResult> useFunc;
    
    protected ItemBuilderAPI(@Nullable ItemBuilderAPI parent) {
        this.propertyMap = new HashMap<>();
        if(Objects.nonNull(parent)) {
            this.creativeTab = parent.creativeTab;
            this.descFunc = parent.descFunc;
            this.stackSize = parent.stackSize;
            this.useFunc = parent.useFunc;
            this.propertyMap.putAll(parent.propertyMap);
        } else {
            this.creativeTab = null;
            this.descFunc = null;
            this.stackSize = 1;
            this.useFunc = null;
        }
        this.itemType = BASIC;
    }
    
    @IndirectCallers
    public ItemBuilderAPI addProperty(ResourceLocationAPI<?> key, BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float> propertyGetter) {
        this.propertyMap.put(key,propertyGetter);
        return this;
    }
    
    public ItemProperties buildProperties() {
        return new ItemProperties(this.creativeTab,this.stackSize,this.registryName,this.descFunc,this.useFunc);
    }
    
    @IndirectCallers
    public ItemBuilderAPI setCreativeTab(CreativeTabAPI tab) {
        this.creativeTab = tab;
        return this;
    }
    
    @IndirectCallers
    public ItemBuilderAPI setItemType(ItemType type) {
        this.itemType = type;
        return this;
    }
    
    @Override public ItemBuilderAPI setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
    
    @IndirectCallers
    public ItemBuilderAPI setStackSize(int size) {
        this.stackSize = Math.max(1,size);
        return this;
    }
    
    @IndirectCallers
    public ItemBuilderAPI setTooltipFunction(BiFunction<ItemStackAPI<?>,WorldAPI<?>,Collection<TextAPI<?>>> descFunc) {
        this.descFunc = descFunc;
        return this;
    }
    
    @IndirectCallers
    public ToolBuilderAPI setToolType(ToolType tool) {
        return RegistryHelper.makeToolBuilder(this,tool);
    }
    
    @IndirectCallers
    public ItemBuilderAPI setUseFunc(Function<TILItemUseContext,ActionResult> func) {
        this.useFunc = func;
        return this;
    }
    
    public enum ItemType{ BASIC, BLOCK, BOW, DISC, FISHING_ROD, TOOL }
    public enum ToolType { AXE, CUSTOM, HOE, PICKAXE, SHOVEL, SWORD }
}