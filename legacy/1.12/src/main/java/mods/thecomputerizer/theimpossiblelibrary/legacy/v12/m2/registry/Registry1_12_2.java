package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public class Registry1_12_2<V extends IForgeRegistryEntry<V>> implements RegistryAPI<RegistryEntry1_12_2<V>> {

    private final IForgeRegistry<V> registry;
    private final ResourceLocation1_12_2 registryKey;

    public Registry1_12_2(IForgeRegistry<V> registry, ResourceLocation1_12_2 registryKey) {
        this.registry = registry;
        this.registryKey = registryKey;
    }

    public IForgeRegistry<V> getBackend() {
        return this.registry;
    }

    @Override
    public ResourceLocation1_12_2 getKey(RegistryEntry1_12_2<V> entry) {
        return entry.getID();
    }

    @Override
    public @Nullable ResourceLocation1_12_2 getKeyNullable(RegistryEntry1_12_2<V> entry) {
        return hasValue(entry) ? entry.getID() : null;
    }

    @Override
    public ResourceLocation1_12_2 getRegistryKey() {
        return this.registryKey;
    }

    @Override
    public RegistryEntryAPI<RegistryEntry1_12_2<V>> getValue(ResourceLocationAPI<?> id) {
        return null;
    }

    @Override
    public @Nullable RegistryEntryAPI<RegistryEntry1_12_2<V>> getValueNullable(ResourceLocationAPI<?> id) {
        return null;
    }

    @Override
    public boolean hasKey(ResourceLocationAPI<?> key) {
        return false;
    }

    @Override
    public boolean hasValue(RegistryEntry1_12_2<V> entry) {
        return false;
    }
}
