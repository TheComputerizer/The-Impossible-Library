package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public class Registry1_16_5<V extends IForgeRegistryEntry<V>> implements RegistryAPI<RegistryEntry1_16_5<V>> {

    private final IForgeRegistry<V> registry;
    private final ResourceLocation1_16_5 registryKey;

    public Registry1_16_5(IForgeRegistry<V> registry, ResourceLocation1_16_5 registryKey) {
        this.registry = registry;
        this.registryKey = registryKey;
    }

    public IForgeRegistry<V> getBackend() {
        return this.registry;
    }

    @Override
    public ResourceLocation1_16_5 getKey(RegistryEntry1_16_5<V> entry) {
        return entry.getID();
    }

    @Override
    public @Nullable ResourceLocation1_16_5 getKeyNullable(RegistryEntry1_16_5<V> entry) {
        return hasValue(entry) ? entry.getID() : null;
    }

    @Override
    public ResourceLocation1_16_5 getRegistryKey() {
        return this.registryKey;
    }

    @Override
    public RegistryEntryAPI<RegistryEntry1_16_5<V>> getValue(ResourceLocationAPI<?> id) {
        return null;
    }

    @Override
    public @Nullable RegistryEntryAPI<RegistryEntry1_16_5<V>> getValueNullable(ResourceLocationAPI<?> id) {
        return null;
    }

    @Override
    public boolean hasKey(ResourceLocationAPI<?> key) {
        return false;
    }

    @Override
    public boolean hasValue(RegistryEntry1_16_5<V> entry) {
        return false;
    }
}
