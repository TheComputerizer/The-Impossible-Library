package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.resource.ResourceLocationForge;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
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

    public RegistryHandlerForge() {
        this.biome = getRegistry(ForgeRegistries.BIOMES,"biome");
        this.block = getRegistry(ForgeRegistries.BLOCKS,"block");
        this.blockEntity = getRegistry(ForgeRegistries.TILE_ENTITIES,"block_entity");
        this.entity = getRegistry(ForgeRegistries.ENTITIES,"entity");
        this.item = getRegistry(ForgeRegistries.ITEMS,"item");
    }

    private <V extends IForgeRegistryEntry<V>> RegistryForge<V> getRegistry(IForgeRegistry<V> registry, String name) {
        ResourceLocationForge key = new ResourceLocationForge(new ResourceLocation(name));
        return new RegistryForge<>(registry,key);
    }

    @Override
    public RegistryEntryAPI<?> getEntryIfPresent(ResourceLocationAPI<?> registryKey, ResourceLocationAPI<?> entryKey) {
        return null;
    }

    @Override
    public RegistryForge<?> getRegistry(ResourceLocationAPI<?> registryKey) {
        switch(registryKey.getPath()) {
            case "biome": return this.biome;
            case "block": return this.block;
            case "block_entity": return this.blockEntity;
            case "entity": return this.entity;
            case "item": return this.item;
            default: return null;
        }
    }

    @Override
    public RegistryForge<?> getBiomeRegistry() {
        return this.biome;
    }

    @Override
    public RegistryForge<?> getBlockRegistry() {
        return this.block;
    }

    @Override
    public RegistryForge<?> getBlockEntityRegistry() {
        return this.blockEntity;
    }

    @Override
    public RegistryForge<?> getEntityRegistry() {
        return this.entity;
    }

    @Override
    public RegistryForge<?> getItemRegistry() {
        return this.item;
    }
}
