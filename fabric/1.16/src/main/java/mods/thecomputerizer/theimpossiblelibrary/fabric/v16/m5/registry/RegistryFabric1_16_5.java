package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.Registry1_16_5;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class RegistryFabric1_16_5<V> extends Registry1_16_5<V> {

    public RegistryFabric1_16_5(Registry<V> registry, ResourceLocationAPI<?> registryKey, Class<V> type) {
        super(registry,type,registryKey);
    }
    
    @SuppressWarnings("unchecked")
    @Override public Registry<V> getBackend() {
        return super.getBackend();
    }

    @Override public ResourceLocationAPI<?> getKey(V value) {
        return WrapperHelper.wrapResourceLocation(getBackend().getKey(value));
    }
    
    @Override public Collection<ResourceLocationAPI<?>> getKeys() {
        return getBackend().keySet().stream()
                .map(WrapperHelper::wrapResourceLocation)
                .collect(Collectors.toSet());
    }
    
    @Override public V getValue(ResourceLocationAPI<?> key) {
        return getBackend().get((ResourceLocation)key.unwrap());
    }
    
    @Override public Collection<V> getValues() {
        return getBackend().stream().collect(Collectors.toSet());
    }
    
    @Override public boolean hasKey(ResourceLocationAPI<?> key) {
        return getBackend().containsKey(key.unwrap());
    }

    @Override public boolean hasValue(V value) {
        return Objects.nonNull(getBackend().getKey(value));
    }
}