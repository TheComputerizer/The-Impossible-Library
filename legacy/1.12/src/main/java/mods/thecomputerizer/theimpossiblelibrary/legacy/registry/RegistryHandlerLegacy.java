package mods.thecomputerizer.theimpossiblelibrary.legacy.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.resource.ResourceLocationLegacy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryHandlerLegacy implements RegistryHandlerAPI<RegistryLegacy<?>> {

    private final RegistryLegacy<Biome> biome;
    private final RegistryLegacy<Block> block;
    private final RegistryLegacy<EntityEntry> entity;
    private final RegistryLegacy<Item> item;

    public RegistryHandlerLegacy() {
        this.biome = getRegistry(ForgeRegistries.BIOMES,"biome");
        this.block = getRegistry(ForgeRegistries.BLOCKS,"block");
        this.entity = getRegistry(ForgeRegistries.ENTITIES,"entity");
        this.item = getRegistry(ForgeRegistries.ITEMS,"item");
    }

    private <V extends IForgeRegistryEntry<V>> RegistryLegacy<V> getRegistry(IForgeRegistry<V> registry, String name) {
        ResourceLocationLegacy key = new ResourceLocationLegacy(new ResourceLocation(name));
        return new RegistryLegacy<>(registry,key);
    }

    @Override
    public RegistryEntryAPI<?> getEntryIfPresent(ResourceLocationAPI<?> registryKey, ResourceLocationAPI<?> entryKey) {
        return null;
    }

    @Override
    public RegistryLegacy<?> getRegistry(ResourceLocationAPI<?> registryKey) {
        switch(registryKey.getPath()) {
            case "biome": return this.biome;
            case "block": return this.block;
            case "entity": return this.entity;
            case "item": return this.item;
            default: return null;
        }
    }

    @Override
    public RegistryLegacy<?> getBiomeRegistry() {
        return this.biome;
    }

    @Override
    public RegistryLegacy<?> getBlockRegistry() {
        return this.block;
    }

    @Override
    public RegistryLegacy<?> getBlockEntityRegistry() {
        return null;
    }

    @Override
    public RegistryLegacy<?> getEntityRegistry() {
        return this.entity;
    }

    @Override
    public RegistryLegacy<?> getItemRegistry() {
        return this.item;
    }
}
