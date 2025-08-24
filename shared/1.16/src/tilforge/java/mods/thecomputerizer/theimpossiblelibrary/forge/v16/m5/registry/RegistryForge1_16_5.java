package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.Registry1_16_5;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;
import java.util.stream.Collectors;

public class RegistryForge1_16_5<V extends IForgeRegistryEntry<V>> extends Registry1_16_5<V> {
    
    public RegistryForge1_16_5(IForgeRegistry<V> forgeRegistry, ResourceLocationAPI<?> registryKey, Class<V> type) {
        super(forgeRegistry,type,registryKey);
    }
    
    @SuppressWarnings("unchecked")
    @Override public IForgeRegistry<V> getBackend() {
        return super.getBackend();
    }

    @Override public ResourceLocationAPI<?> getKey(V value) {
        return WrapperHelper.wrapResourceLocation(getBackend().getKey(value));
    }
    
    @Override public Collection<ResourceLocationAPI<?>> getKeys() {
        return getBackend().getKeys().stream()
                .map(WrapperHelper::wrapResourceLocation)
                .collect(Collectors.toSet());
    }
    
    @Override public V getValue(ResourceLocationAPI<?> key) {
        return getBackend().getValue(key.unwrap());
    }
    
    @Override public Collection<V> getValues() {
        return getBackend().getValues();
    }
    
    @Override public boolean hasKey(ResourceLocationAPI<?> key) {
        return getBackend().containsKey(key.unwrap());
    }

    @Override public boolean hasValue(V value) {
        return getBackend().containsValue(value);
    }
}