package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.Registry1_18_2;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;
import java.util.stream.Collectors;

public class RegistryForge1_18_2<V extends IForgeRegistryEntry<V>> extends Registry1_18_2<V> {
    
    public RegistryForge1_18_2(IForgeRegistry<V> forgeRegistry, ResourceLocationAPI<?> registryKey, Class<V> type) {
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