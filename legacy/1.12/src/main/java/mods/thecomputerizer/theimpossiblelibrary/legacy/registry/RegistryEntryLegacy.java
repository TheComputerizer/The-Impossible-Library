package mods.thecomputerizer.theimpossiblelibrary.legacy.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.resource.ResourceLocationLegacy;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class RegistryEntryLegacy<V extends IForgeRegistryEntry<V>> implements RegistryEntryAPI<V> {

    private final V entry;
    private final ResourceLocationLegacy registryName;

    protected RegistryEntryLegacy(V entry) {
        this.entry = entry;
        this.registryName = new ResourceLocationLegacy(entry.getRegistryName());
    }

    @Override
    public ResourceLocationLegacy getID() {
        return this.registryName;
    }

    @Override
    public V getValue() {
        return this.entry;
    }

    protected abstract RegistryLegacy<V> getRegistry();

    @Override
    public ResourceLocationLegacy getRegistryKey() {
        return getRegistry().getRegistryKey();
    }
}
