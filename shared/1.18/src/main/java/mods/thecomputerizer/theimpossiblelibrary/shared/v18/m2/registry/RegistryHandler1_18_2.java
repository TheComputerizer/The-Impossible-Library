package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry;

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
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.block.BlockBuilder1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.blockentity.BlockEntityBuilder1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.entity.EntityBuilder1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.item.DiscBuilder1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.item.ItemBlockBuilder1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.item.ItemBuilder1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.item.ToolBuilder1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.sound.SoundBuilder1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.tab.CreativeTabBuilder1_18_2;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class RegistryHandler1_18_2 implements RegistryHandlerAPI {

    private final Set<? super Registry1_18_2<?>> registries;
    protected Registry1_18_2<?> biome;
    protected Registry1_18_2<?> block;
    protected Registry1_18_2<?> blockEntity;
    protected Registry1_18_2<?> effect;
    protected Registry1_18_2<?> entity;
    protected Registry1_18_2<?> item;
    protected Registry1_18_2<?> potion;
    protected Registry1_18_2<?> sound;
    protected Registry1_18_2<?> structure;

    public RegistryHandler1_18_2() {
        Set<? super Registry1_18_2<?>> registries = new HashSet<>();
        collectRegistries(registries);
        this.registries = Collections.unmodifiableSet(registries);
    }
    
    protected abstract void collectRegistries(Set<? super Registry1_18_2<?>> registries);

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
        return switch(registryKey.getPath()) {
            case "biome" -> this.biome;
            case "block" -> this.block;
            case "block_entity" -> this.blockEntity;
            case "effect" -> this.effect;
            case "entity" -> this.entity;
            case "item" -> this.item;
            case "potion" -> this.potion;
            case "sound" -> this.sound;
            case "structure" -> this.structure;
            default -> null;
        };
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
        return this.structure;
    }
    
    @Override public BlockBuilderAPI makeBlockBuilder(@Nullable BlockBuilderAPI parent) {
        return new BlockBuilder1_18_2(parent);
    }
    
    @Override public BlockEntityBuilderAPI makeBlockEntityBuilder(@Nullable BlockEntityBuilderAPI parent) {
        return new BlockEntityBuilder1_18_2(parent);
    }
    
    @Override public CreativeTabBuilderAPI makeCreativeTabBuilder() {
        return new CreativeTabBuilder1_18_2();
    }
    
    @Override public DiscBuilderAPI makeDiscBuilder(@Nullable ItemBuilderAPI parent) {
        return new DiscBuilder1_18_2(parent);
    }
    
    @Override public EntityBuilderAPI makeEntityBuilder(@Nullable EntityBuilderAPI parent) {
        return new EntityBuilder1_18_2(parent);
    }
    
    @Override public ItemBlockBuilderAPI makeItemBlockBuilder(@Nullable ItemBuilderAPI parent) {
        return new ItemBlockBuilder1_18_2(parent);
    }
    
    @Override public ItemBuilderAPI makeItemBuilder(@Nullable ItemBuilderAPI parent) {
        return new ItemBuilder1_18_2(parent);
    }
    
    @Override public SoundBuilderAPI makeSoundBuilder(@Nullable SoundBuilderAPI parent) {
        return new SoundBuilder1_18_2(parent);
    }
    
    @Override public ToolBuilderAPI makeToolBuilder(@Nullable ItemBuilderAPI parent, ToolType tool) {
        return new ToolBuilder1_18_2(parent,tool);
    }
}