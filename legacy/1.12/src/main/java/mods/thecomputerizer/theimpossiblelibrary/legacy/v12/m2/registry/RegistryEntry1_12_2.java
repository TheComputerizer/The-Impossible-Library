package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class RegistryEntry1_12_2<V extends IForgeRegistryEntry<V>> implements RegistryEntryAPI<V> {

    private final V entry;
    private final ResourceLocation1_12_2 registryName;

    protected RegistryEntry1_12_2(V entry) {
        this.entry = entry;
        this.registryName = new ResourceLocation1_12_2(entry.getRegistryName());
    }

    @Override
    public ResourceLocation1_12_2 getID() {
        return this.registryName;
    }

    @Override
    public V getValue() {
        return this.entry;
    }

    protected abstract Registry1_12_2<V> getRegistry();

    @Override
    public ResourceLocation1_12_2 getRegistryKey() {
        return getRegistry().getRegistryKey();
    }
}
