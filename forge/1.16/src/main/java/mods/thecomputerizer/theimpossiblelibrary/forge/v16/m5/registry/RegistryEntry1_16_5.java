package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class RegistryEntry1_16_5<V extends IForgeRegistryEntry<V>> implements RegistryEntryAPI<V> {

    private final V entry;
    private final ResourceLocation1_16_5 registryName;

    protected RegistryEntry1_16_5(V entry) {
        this.entry = entry;
        this.registryName = new ResourceLocation1_16_5(entry.getRegistryName());
    }

    @Override
    public ResourceLocation1_16_5 getID() {
        return this.registryName;
    }

    @Override
    public V getValue() {
        return this.entry;
    }

    protected abstract Registry1_16_5<V> getRegistry();

    @Override
    public ResourceLocation1_16_5 getRegistryKey() {
        return getRegistry().getRegistryKey();
    }
}
