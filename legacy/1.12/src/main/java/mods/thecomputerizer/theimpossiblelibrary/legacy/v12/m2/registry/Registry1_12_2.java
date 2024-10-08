package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class Registry1_12_2<V extends IForgeRegistryEntry<V>> extends RegistryAPI<V> {

    public Registry1_12_2(IForgeRegistry<V> forgeRegistry, Class<V> type, ResourceLocationAPI<?> registryKey) {
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
        return getBackend().getKeys().stream().map(WrapperHelper::wrapResourceLocation).collect(Collectors.toSet());
    }
    
    @Override public V getValue(ResourceLocationAPI<?> key) {
        return getBackend().getValue(key.unwrap());
    }
    
    @Override public Collection<V> getValues() {
        return getBackend().getValuesCollection();
    }
    
    @Override public boolean hasKey(ResourceLocationAPI<?> key) {
        return getBackend().containsKey(key.unwrap());
    }

    @Override public boolean hasValue(V value) {
        return getBackend().containsValue(value);
    }
}