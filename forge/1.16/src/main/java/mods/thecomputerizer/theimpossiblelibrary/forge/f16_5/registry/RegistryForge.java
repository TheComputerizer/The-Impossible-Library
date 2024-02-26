package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.resource.ResourceLocationForge;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public class RegistryForge<V extends IForgeRegistryEntry<V>> implements RegistryAPI<RegistryEntryForge<V>> {

    private final IForgeRegistry<V> registry;
    private final ResourceLocationForge registryKey;

    public RegistryForge(IForgeRegistry<V> registry, ResourceLocationForge registryKey) {
        this.registry = registry;
        this.registryKey = registryKey;
    }

    public IForgeRegistry<V> getBackend() {
        return this.registry;
    }

    @Override
    public ResourceLocationForge getKey(RegistryEntryForge<V> entry) {
        return entry.getID();
    }

    @Override
    public @Nullable ResourceLocationForge getKeyNullable(RegistryEntryForge<V> entry) {
        return hasValue(entry) ? entry.getID() : null;
    }

    @Override
    public ResourceLocationForge getRegistryKey() {
        return this.registryKey;
    }

    @Override
    public RegistryEntryAPI<RegistryEntryForge<V>> getValue(ResourceLocationAPI<?> id) {
        return null;
    }

    @Override
    public @Nullable RegistryEntryAPI<RegistryEntryForge<V>> getValueNullable(ResourceLocationAPI<?> id) {
        return null;
    }

    @Override
    public boolean hasKey(ResourceLocationAPI<?> key) {
        return false;
    }

    @Override
    public boolean hasValue(RegistryEntryForge<V> entry) {
        return false;
    }
}
