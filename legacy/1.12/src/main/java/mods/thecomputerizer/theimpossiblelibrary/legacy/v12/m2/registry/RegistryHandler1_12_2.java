package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
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

public class RegistryHandler1_12_2 implements RegistryHandlerAPI<Registry1_12_2<?>> {

    private final Registry1_12_2<Biome> biome;
    private final Registry1_12_2<Block> block;
    private final Registry1_12_2<EntityEntry> entity;
    private final Registry1_12_2<Item> item;
    private final Registry1_12_2<SoundEvent> sound;

    public RegistryHandler1_12_2() {
        this.biome = getRegistry(ForgeRegistries.BIOMES,"biome");
        this.block = getRegistry(ForgeRegistries.BLOCKS,"block");
        this.entity = getRegistry(ForgeRegistries.ENTITIES,"entity");
        this.item = getRegistry(ForgeRegistries.ITEMS,"item");
        this.sound = getRegistry(ForgeRegistries.SOUND_EVENTS,"sound");
    }

    private <V extends IForgeRegistryEntry<V>> Registry1_12_2<V> getRegistry(IForgeRegistry<V> registry, String name) {
        ResourceLocation1_12_2 key = new ResourceLocation1_12_2(new ResourceLocation(name));
        return new Registry1_12_2<>(registry,key);
    }

    @Override
    public @Nullable RegistryEntryAPI<?> getEntryIfPresent(ResourceLocationAPI<?> registryKey, ResourceLocationAPI<?> entryKey) {
        RegistryAPI<?> reg = getRegistry(registryKey);
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
    public Registry1_12_2<SoundEvent> getSoundRegistry() {
        return this.sound;
    }
}
