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
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;


public abstract class DiscBuilderAPI extends ItemBuilderAPI {
    
    protected Function<ItemStackAPI<?>,TextAPI<?>> nameSupplier;
    private SoundEventAPI<?> sound;
    protected int lengthInSeconds = 60;
    
    protected DiscBuilderAPI(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public DiscBuilderAPI addProperty(ResourceLocationAPI<?> key, BiFunction<ItemStackAPI<?>,WorldAPI<?>,Float> propertyGetter) {
        this.propertyMap.put(key,propertyGetter);
        return this;
    }
    
    protected abstract <S> S defaultSound(); //TODO Replace with empty sound event
    
    @SuppressWarnings("unchecked") protected <S> S getSound() {
        return Objects.nonNull(this.sound) ? this.sound.unwrap() : (S)defaultSound();
    }
    
    @Override public DiscBuilderAPI setCreativeTab(CreativeTabAPI<?> tab) {
        this.creativeTab = tab;
        return this;
    }
    
    public DiscBuilderAPI setItemType(ItemType type) {
        this.itemType = type;
        return this;
    }
    
    public DiscBuilderAPI setLengthInSeconds(int length) {
        this.lengthInSeconds = length;
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
