package mods.thecomputerizer.theimpossiblelibrary.legacy.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.resource.ResourceLocationLegacy;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public class RegistryLegacy<V extends IForgeRegistryEntry<V>> implements RegistryAPI<RegistryEntryLegacy<V>> {

    private final IForgeRegistry<V> registry;
    private final ResourceLocationLegacy registryKey;

    public RegistryLegacy(IForgeRegistry<V> registry, ResourceLocationLegacy registryKey) {
        this.registry = registry;
        this.registryKey = registryKey;
    }

    public IForgeRegistry<V> getBackend() {
        return this.registry;
    }

    @Override
    public ResourceLocationLegacy getKey(RegistryEntryLegacy<V> entry) {
        return entry.getID();
    }

    @Override
    public @Nullable ResourceLocationLegacy getKeyNullable(RegistryEntryLegacy<V> entry) {
        return hasValue(entry) ? entry.getID() : null;
    }

    @Override
    public ResourceLocationLegacy getRegistryKey() {
        return this.registryKey;
    }

    @Override
    public RegistryEntryAPI<RegistryEntryLegacy<V>> getValue(ResourceLocationAPI<?> id) {
        return null;
    }

    @Override
    public @Nullable RegistryEntryAPI<RegistryEntryLegacy<V>> getValueNullable(ResourceLocationAPI<?> id) {
        return null;
    }

    @Override
    public boolean hasKey(ResourceLocationAPI<?> key) {
        return false;
    }

    @Override
    public boolean hasValue(RegistryEntryLegacy<V> entry) {
        return false;
    }
}
