package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.DiscBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI.ToolType;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ToolBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.sound.SoundBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.tab.CreativeTabBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.BasicWrapped;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.block.BlockBuilder1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.entity.EntityBuilder1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.item.DiscBuilder1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.item.ItemBlockBuilder1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.item.ItemBuilder1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.item.ToolBuilder1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.sound.SoundBuilder1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.tab.CreativeTabBuilder1_16_5;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class RegistryHandler1_16_5 implements RegistryHandlerAPI {

    private final Set<? super Registry1_16_5<?>> registries;
    protected Registry1_16_5<?> biome;
    protected Registry1_16_5<?> block;
    protected Registry1_16_5<?> blockEntity;
    protected Registry1_16_5<?> effect;
    protected Registry1_16_5<?> entity;
    protected Registry1_16_5<?> item;
    protected Registry1_16_5<?> potion;
    protected Registry1_16_5<?> sound;
    protected Registry1_16_5<?> structure;

    public RegistryHandler1_16_5() {
        Set<? super Registry1_16_5<?>> registries = new HashSet<>();
        collectRegistries(registries);
        this.registries = Collections.unmodifiableSet(registries);
    }
    
    protected abstract void collectRegistries(Set<? super Registry1_16_5<?>> registries);

    @Override public <V> @Nullable V getEntryIfPresent(ResourceLocationAPI<?> registryKey, ResourceLocationAPI<?> entryKey) {
        RegistryAPI<?> reg = getRegistry(registryKey);
        return reg.hasKey(entryKey) ? BasicWrapped.cast(reg.getValue(entryKey)) : null;
    }

    @Override public RegistryAPI<?> getBiomeRegistry() {
        return this.biome;
    }
    
    @Override public RegistryAPI<?> getBlockRegistry() {
        return this.block;
    }
    
    @Override public RegistryAPI<?> getBlockEntityRegistry() {
        return this.blockEntity;
    }
    
    @Override public RegistryAPI<?> getEffectRegistry() {
        return this.effect;
    }
    
    @Override public RegistryAPI<?> getEntityRegistry() {
        return this.entity;
    }
    
    @Override public RegistryAPI<?> getItemRegistry() {
        return this.item;
    }
    
    @Override public RegistryAPI<?> getPotionRegistry() {
        return this.potion;
    }

    @Override public RegistryAPI<?> getRegistry(ResourceLocationAPI<?> registryKey) {
        switch(registryKey.getPath()) {
            case "biome": return this.biome;
            case "block": return this.block;
            case "block_entity": return this.blockEntity;
            case "effect": return this.effect;
            case "entity": return this.entity;
            case "item": return this.item;
            case "potion": return this.potion;
            case "sound": return this.sound;
            case "structure": return this.structure;
            default: return null;
        }
    }

    @Override public RegistryAPI<?> getRegistry(Class<?> type) {
        for(Object element : this.registries) {
            RegistryAPI<?> registry = (RegistryAPI<?>)element;
            if(registry.getType().isAssignableFrom(type)) return registry;
        }
        return null;
    }
    
    @Override public RegistryAPI<?> getSoundRegistry() {
        return this.sound;
    }
    
    @Override public RegistryAPI<?> getStructureRegistry() {
        return this.sound;
    }
    
    @Override public BlockBuilderAPI makeBlockBuilder(@Nullable BlockBuilderAPI parent) {
        return new BlockBuilder1_16_5(parent);
    }
    
    @Override public abstract BlockEntityBuilderAPI makeBlockEntityBuilder(@Nullable BlockEntityBuilderAPI parent);
    
    @Override public CreativeTabBuilderAPI makeCreativeTabBuilder() {
        return new CreativeTabBuilder1_16_5();
    }
    
    @Override public DiscBuilderAPI makeDiscBuilder(@Nullable ItemBuilderAPI parent) {
        return new DiscBuilder1_16_5(parent);
    }
    
    @Override public EntityBuilderAPI makeEntityBuilder(@Nullable EntityBuilderAPI parent) {
        return new EntityBuilder1_16_5(parent);
    }
    
    @Override public ItemBlockBuilderAPI makeItemBlockBuilder(@Nullable ItemBuilderAPI parent) {
        return new ItemBlockBuilder1_16_5(parent);
    }
    
    @Override public ItemBuilderAPI makeItemBuilder(@Nullable ItemBuilderAPI parent) {
        return new ItemBuilder1_16_5(parent);
    }
    
    @Override public SoundBuilderAPI makeSoundBuilder(@Nullable SoundBuilderAPI parent) {
        return new SoundBuilder1_16_5(parent);
    }
    
    @Override public ToolBuilderAPI makeToolBuilder(@Nullable ItemBuilderAPI parent, ToolType tool) {
        return new ToolBuilder1_16_5(parent,tool);
    }
}