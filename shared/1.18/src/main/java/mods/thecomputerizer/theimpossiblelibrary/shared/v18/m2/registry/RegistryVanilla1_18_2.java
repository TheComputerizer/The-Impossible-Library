package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class RegistryVanilla1_18_2<V> extends Registry1_18_2<V> {

    public RegistryVanilla1_18_2(Registry<V> registry, ResourceLocationAPI<?> registryKey, Class<?> type) {
        super(registry,GenericUtils.cast(type),registryKey);
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
        return getBackend().containsKey((ResourceLocation)key.unwrap());
    }

    @Override public boolean hasValue(V value) {
        return Objects.nonNull(getBackend().getKey(value));
    }
}