package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI.ToolType;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.sound.SoundBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.block.BlockBuilder1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.blockentity.BlockEntityBuilder1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.entity.EntityBuilder1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.item.DiscBuilder1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.item.ItemBlockBuilder1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.item.ItemBuilder1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.item.ToolBulder1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.sound.SoundBuilder1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.tab.CreativeTabBuilder1_16_5;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;

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

    public RegistryHandler1_16_5() {
        Set<? super Registry1_16_5<?>> registries = new HashSet<>();
        collectRegistries(registries);
        this.registries = Collections.unmodifiableSet(registries);
    }
    
    protected abstract void collectRegistries(Set<? super Registry1_16_5<?>> registries);

    @SuppressWarnings("unchecked")
    @Override
    public <V> @Nullable V getEntryIfPresent(ResourceLocationAPI<?> registryKey, ResourceLocationAPI<?> entryKey) {
        RegistryAPI<V> reg = (RegistryAPI<V>)getRegistry(registryKey);
        return reg.hasKey(entryKey) ? reg.getValue(entryKey) : null;
    }

    @SuppressWarnings("unchecked") @Override
    public Registry1_16_5<Biome> getBiomeRegistry() {
        return (Registry1_16_5<Biome>)this.biome;
    }
    
    @SuppressWarnings("unchecked") @Override
    public Registry1_16_5<Block> getBlockRegistry() {
        return (Registry1_16_5<Block>)this.block;
    }
    
    @Override
    public Registry1_16_5<?> getBlockEntityRegistry() {
        return this.blockEntity;
    }
    
    @SuppressWarnings("unchecked") @Override
    public Registry1_16_5<Effect> getEffectRegistry() {
        return (Registry1_16_5<Effect>)this.effect;
    }
    
    @Override
    public Registry1_16_5<?> getEntityRegistry() {
        return this.entity;
    }
    
    @SuppressWarnings("unchecked") @Override
    public Registry1_16_5<Item> getItemRegistry() {
        return (Registry1_16_5<Item>)this.item;
    }
    
    @SuppressWarnings("unchecked") @Override
    public Registry1_16_5<Potion> getPotionRegistry() {
        return (Registry1_16_5<Potion>)this.potion;
    }

    @Override
    public Registry1_16_5<?> getRegistry(ResourceLocationAPI<?> registryKey) {
        switch(registryKey.getPath()) {
            case "biome": return this.biome;
            case "block": return this.block;
            case "block_entity": return this.blockEntity;
            case "effect": return this.effect;
            case "entity": return this.entity;
            case "item": return this.item;
            case "potion": return this.potion;
            case "sound": return this.sound;
            default: return null;
        }
    }

    @Override
    public Registry1_16_5<?> getRegistry(Class<?> type) {
        for(Object element : this.registries) {
            Registry1_16_5<?> registry = (Registry1_16_5<?>)element;
            if(registry.getType().isAssignableFrom(type)) return registry;
        }
        return null;
    }
    
    @SuppressWarnings("unchecked") @Override
    public Registry1_16_5<SoundEvent> getSoundRegistry() {
        return (Registry1_16_5<SoundEvent>)this.sound;
    }
    
    @Override public BlockBuilder1_16_5 makeBlockBuilder(@Nullable BlockBuilderAPI parent) {
        return new BlockBuilder1_16_5(parent);
    }
    
    @Override public BlockEntityBuilder1_16_5 makeBlockEntityBuilder(@Nullable BlockEntityBuilderAPI parent) {
        return new BlockEntityBuilder1_16_5(parent);
    }
    
    @Override public CreativeTabBuilder1_16_5 makeCreativeTabBuilder() {
        return new CreativeTabBuilder1_16_5();
    }
    
    @Override public DiscBuilder1_16_5 makeDiscBuilder(@Nullable ItemBuilderAPI parent) {
        return new DiscBuilder1_16_5(parent);
    }
    
    @Override public EntityBuilder1_16_5 makeEntityBuilder(@Nullable EntityBuilderAPI parent) {
        return new EntityBuilder1_16_5(parent);
    }
    
    @Override public ItemBlockBuilder1_16_5 makeItemBlockBuilder(@Nullable ItemBuilderAPI parent) {
        return new ItemBlockBuilder1_16_5(parent);
    }
    
    @Override public ItemBuilder1_16_5 makeItemBuilder(@Nullable ItemBuilderAPI parent) {
        return new ItemBuilder1_16_5(parent);
    }
    
    @Override public SoundBuilder1_16_5 makeSoundBuilder(@Nullable SoundBuilderAPI parent) {
        return new SoundBuilder1_16_5(parent);
    }
    
    @Override public ToolBulder1_16_5 makeToolBuilder(@Nullable ItemBuilderAPI parent, ToolType tool) {
        return new ToolBulder1_16_5(parent,tool);
    }
}
