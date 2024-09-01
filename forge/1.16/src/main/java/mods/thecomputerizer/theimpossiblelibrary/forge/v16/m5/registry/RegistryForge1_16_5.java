package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.Registry1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryForge1_16_5<V extends IForgeRegistryEntry<V>> extends Registry1_16_5<V> {
    
    public RegistryForge1_16_5(IForgeRegistry<V> forgeRegistry, ResourceLocation1_16_5 registryKey, Class<V> type) {
        super(forgeRegistry,type,registryKey);
    }
    
    @SuppressWarnings("unchecked")
    @Override public IForgeRegistry<V> getBackend() {
        return (IForgeRegistry<V>)this.backend;
    }

    @Override
    public ResourceLocation1_16_5 getKey(V value) {
        return new ResourceLocation1_16_5(getBackend().getKey(value));
    }

    @Override
    public V getValue(ResourceLocationAPI<?> key) {
        return getBackend().getValue(((ResourceLocation1_16_5)key).getInstance());
    }

    @Override
    public boolean hasKey(ResourceLocationAPI<?> key) {
        return getBackend().containsKey(((ResourceLocation1_16_5)key).getInstance());
    }

    @Override
    public boolean hasValue(V value) {
        return getBackend().containsValue(value);
    }
}
