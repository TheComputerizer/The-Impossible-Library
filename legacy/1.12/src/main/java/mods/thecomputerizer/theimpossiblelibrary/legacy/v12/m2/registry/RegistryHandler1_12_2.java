package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RegistryHandler1_12_2 implements RegistryHandlerAPI<Registry1_12_2<?>> {

    private final Set<Registry1_12_2<?>> registries;
    private final Registry1_12_2<Biome> biome;
    private final Registry1_12_2<Block> block;
    private final Registry1_12_2<EntityEntry> entity;
    private final Registry1_12_2<Item> item;
    private final Registry1_12_2<SoundEvent> sound;

    public RegistryHandler1_12_2() {
        Set<Registry1_12_2<?>> registries = new HashSet<>();
        this.biome = getRegistry(registries,ForgeRegistries.BIOMES,"biome",Biome.class);
        this.block = getRegistry(registries,ForgeRegistries.BLOCKS,"block",Block.class);
        this.entity = getRegistry(registries,ForgeRegistries.ENTITIES,"entity",EntityEntry.class);
        this.item = getRegistry(registries,ForgeRegistries.ITEMS,"item",Item.class);
        this.sound = getRegistry(registries,ForgeRegistries.SOUND_EVENTS,"sound",SoundEvent.class);
        this.registries = Collections.unmodifiableSet(registries);
    }

    private <V extends IForgeRegistryEntry<V>> Registry1_12_2<V> getRegistry(
            Set<Registry1_12_2<?>> registries, IForgeRegistry<V> forgeRegistry, String name, Class<V> type) {
        ResourceLocation1_12_2 key = new ResourceLocation1_12_2(new ResourceLocation(name));
        Registry1_12_2<V> registry = new Registry1_12_2<>(forgeRegistry,type,key);
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
    public Registry1_12_2<Biome> getBiomeRegistry() {
        return this.biome;
    }

    @Override
    public Registry1_12_2<Block> getBlockRegistry() {
        return this.block;
    }

    @Override
    public Registry1_12_2<?> getBlockEntityRegistry() {
        return null;
    }

    @Override
    public Registry1_12_2<EntityEntry> getEntityRegistry() {
        return this.entity;
    }

    @Override
    public Registry1_12_2<Item> getItemRegistry() {
        return this.item;
    }

    @Override
    public Registry1_12_2<?> getRegistry(ResourceLocationAPI<?> registryKey) {
        switch(registryKey.getPath()) {
            case "biome": return this.biome;
            case "block": return this.block;
            case "entity": return this.entity;
            case "item": return this.item;
            default: return null;
        }
    }

    @Override
    public Registry1_12_2<?> getRegistry(Class<?> type) {
        for(Registry1_12_2<?> registry : this.registries)
            if(registry.getType().isAssignableFrom(type)) return registry;
        return null;
    }

    @Override
    public Registry1_12_2<SoundEvent> getSoundRegistry() {
        return this.sound;
    }
}
