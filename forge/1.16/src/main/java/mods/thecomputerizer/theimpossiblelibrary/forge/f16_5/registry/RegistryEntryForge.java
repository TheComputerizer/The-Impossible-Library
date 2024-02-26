package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.resource.ResourceLocationForge;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class RegistryEntryForge<V extends IForgeRegistryEntry<V>> implements RegistryEntryAPI<V> {

    private final V entry;
    private final ResourceLocationForge registryName;

    protected RegistryEntryForge(V entry) {
        this.entry = entry;
        this.registryName = new ResourceLocationForge(entry.getRegistryName());
    }

    @Override
    public ResourceLocationForge getID() {
        return this.registryName;
    }

    @Override
    public V getValue() {
        return this.entry;
    }

    protected abstract RegistryForge<V> getRegistry();

    @Override
    public ResourceLocationForge getRegistryKey() {
        return getRegistry().getRegistryKey();
    }
}
