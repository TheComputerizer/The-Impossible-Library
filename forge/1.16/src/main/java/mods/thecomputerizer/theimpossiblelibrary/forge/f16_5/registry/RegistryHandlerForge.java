package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.resource.ResourceLocationForge;
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

public class RegistryHandlerForge implements RegistryHandlerAPI<RegistryForge<?>> {

    private final RegistryForge<Biome> biome;
    private final RegistryForge<Block> block;
    private final RegistryForge<TileEntityType<?>> blockEntity;
    private final RegistryForge<EntityType<?>> entity;
    private final RegistryForge<Item> item;
    private final RegistryForge<SoundEvent> sound;

    public RegistryHandlerForge() {
        this.biome = getRegistry(ForgeRegistries.BIOMES,"biome");
        this.block = getRegistry(ForgeRegistries.BLOCKS,"block");
        this.blockEntity = getRegistry(ForgeRegistries.TILE_ENTITIES,"block_entity");
        this.entity = getRegistry(ForgeRegistries.ENTITIES,"entity");
        this.item = getRegistry(ForgeRegistries.ITEMS,"item");
        this.sound = getRegistry(ForgeRegistries.SOUND_EVENTS,"sound");
    }

    private <V extends IForgeRegistryEntry<V>> RegistryForge<V> getRegistry(IForgeRegistry<V> registry, String name) {
        ResourceLocationForge key = new ResourceLocationForge(new ResourceLocation(name));
        return new RegistryForge<>(registry,key);
    }

    @Override
    public RegistryEntryAPI<?> getEntryIfPresent(ResourceLocationAPI<?> registryKey, ResourceLocationAPI<?> entryKey) {
        RegistryAPI<?> reg = getRegistry(registryKey);
        return reg.hasKey(entryKey) ? reg.getValue(entryKey) : null;
    }

    @Override
    public RegistryForge<Biome> getBiomeRegistry() {
        return this.biome;
    }

    @Override
    public RegistryForge<Block> getBlockRegistry() {
        return this.block;
    }

    @Override
    public RegistryForge<TileEntityType<?>> getBlockEntityRegistry() {
        return this.blockEntity;
    }

    @Override
    public RegistryForge<EntityType<?>> getEntityRegistry() {
        return this.entity;
    }

    @Override
    public RegistryForge<Item> getItemRegistry() {
        return this.item;
    }

    @Override
    public RegistryForge<?> getRegistry(ResourceLocationAPI<?> registryKey) {
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
    public RegistryForge<SoundEvent> getSoundRegistry() {
        return this.sound;
    }
}
