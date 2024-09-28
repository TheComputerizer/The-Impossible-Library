package mods.thecomputerizer.theimpossiblelibrary.api.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolTierAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI.ItemType.TOOL;

@SuppressWarnings("unused")
public abstract class ToolBuilderAPI extends ItemBuilderAPI {
    
    protected ToolType toolType;
    protected float damageModifier;
    protected Collection<BlockAPI<?>> effectiveBlocks;
    protected float speedModifier;
    protected ToolTierAPI<?> toolTier;
    
    protected ToolBuilderAPI(@Nullable ItemBuilderAPI parent, ToolType type) {
        super(parent);
        this.itemType = TOOL;
        this.toolType = type;
        this.damageModifier = 1f;
        this.effectiveBlocks = new HashSet<>();
        this.speedModifier = 1f;
        this.toolTier = ItemHelper.getToolTier("wood");
    }
    
    public ToolBuilderAPI addEffectiveBlock(BlockAPI<?> block) {
        this.effectiveBlocks.add(block);
        return this;
    }
    
    public ToolBuilderAPI addEffectiveBlocks(Collection<BlockAPI<?>> blocks) {
        this.effectiveBlocks.addAll(blocks);
        return this;
    }
    
    @Override public ToolBuilderAPI addProperty(ResourceLocationAPI<?> key, BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float> propertyGetter) {
        this.propertyMap.put(key,propertyGetter);
        return this;
    }
    
    @Override public ToolBuilderAPI setCreativeTab(CreativeTabAPI tab) {
        this.creativeTab = tab;
        return this;
    }
    
    public ToolBuilderAPI setDamageModifier(float mod) {
        this.damageModifier = mod;
        return this;
    }
    
    public ToolBuilderAPI setEffectiveBlocks(Collection<BlockAPI<?>> blocks) {
        this.effectiveBlocks = Objects.nonNull(blocks) ? blocks : new HashSet<>();
        return this;
    }
    
    public ToolBuilderAPI setItemType(ItemType type) {
        this.itemType = type;
        return this;
    }
    
    @Override public ToolBuilderAPI setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
    
    public ToolBuilderAPI setSpeedModifier(float speed) {
        this.speedModifier = speed;
        return this;
    }
    
    public ToolBuilderAPI setStackSize(int size) {
        this.stackSize = Math.max(1,size);
        return this;
    }
    
    public ToolBuilderAPI setToolTier(String name) {
        return setToolTier(ItemHelper.getToolTier(name));
    }
    
    public ToolBuilderAPI setToolTier(ToolTierAPI<?> tier) {
        this.toolTier = tier;
        return this;
    }
    
    public ToolBuilderAPI setTooltipFunction(BiFunction<ItemStackAPI<?>,WorldAPI<?>,Collection<TextAPI<?>>> descFunc) {
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