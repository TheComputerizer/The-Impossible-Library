package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RegistryHandler1_16_5 implements RegistryHandlerAPI<Registry1_16_5<?>> {

    private final Set<Registry1_16_5<?>> registries;
    private final Registry1_16_5<Biome> biome;
    private final Registry1_16_5<Block> block;
    private final Registry1_16_5<TileEntityType<?>> blockEntity;
    private final Registry1_16_5<EntityType<?>> entity;
    private final Registry1_16_5<Item> item;
    private final Registry1_16_5<SoundEvent> sound;

    public RegistryHandler1_16_5() {
        Set<Registry1_16_5<?>> registries = new HashSet<>();
        this.biome = getRegistry(registries,ForgeRegistries.BIOMES,"biome",Biome.class);
        this.block = getRegistry(registries,ForgeRegistries.BLOCKS,"block",Block.class);
        this.blockEntity = getRegistry(registries,ForgeRegistries.TILE_ENTITIES,"block_entity",TileEntityType.class);
        this.entity = getRegistry(registries,ForgeRegistries.ENTITIES,"entity",EntityType.class);
        this.item = getRegistry(registries,ForgeRegistries.ITEMS,"item",Item.class);
        this.sound = getRegistry(registries,ForgeRegistries.SOUND_EVENTS,"sound",SoundEvent.class);
        this.registries = Collections.unmodifiableSet(registries);
    }

    @SuppressWarnings("unchecked")
    private <V extends IForgeRegistryEntry<V>> Registry1_16_5<V> getRegistry(
            Set<Registry1_16_5<?>> registries, IForgeRegistry<V> forgeRegistry, String name, Class<?> type) {
        ResourceLocation1_16_5 key = new ResourceLocation1_16_5(new ResourceLocation(name));
        Registry1_16_5<V> registry = new Registry1_16_5<>(forgeRegistry,key,(Class<V>)type);
        registries.add(registry);
        return registry;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> @Nullable V getEntryIfPresent(ResourceLocationAPI<?> registryKey, ResourceLocationAPI<?> entryKey) {
        RegistryAPI<V> reg = (RegistryAPI<V>)getRegistry(registryKey);
        return reg.hasKey(entryKey) ? reg.getValue(entryKey) : null;
    }

    @Override
    public Registry1_16_5<Biome> getBiomeRegistry() {
        return this.biome;
    }

    @Override
    public Registry1_16_5<Block> getBlockRegistry() {
        return this.block;
    }

    @Override
    public Registry1_16_5<TileEntityType<?>> getBlockEntityRegistry() {
        return this.blockEntity;
    }

    @Override
    public Registry1_16_5<EntityType<?>> getEntityRegistry() {
        return this.entity;
    }

    @Override
    public Registry1_16_5<Item> getItemRegistry() {
        return this.item;
    }

    @Override
    public Registry1_16_5<?> getRegistry(ResourceLocationAPI<?> registryKey) {
        switch(registryKey.getPath()) {
            case "biome": return this.biome;
            case "block": return this.block;
            case "block_entity": return this.blockEntity;
            case "entity": return this.entity;
            case "item": return this.item;
            case "sound": return this.sound;
            default: return null;
        }
    }

    @Override
    public Registry1_16_5<?> getRegistry(Class<?> type) {
        for(Registry1_16_5<?> registry : this.registries)
            if(registry.getType().isAssignableFrom(type)) return registry;
        return null;
    }

    @Override
    public Registry1_16_5<SoundEvent> getSoundRegistry() {
        return this.sound;
    }
}
