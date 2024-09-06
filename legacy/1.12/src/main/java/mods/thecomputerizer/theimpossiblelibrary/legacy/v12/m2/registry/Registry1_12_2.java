package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Getter
public class Registry1_12_2<V extends IForgeRegistryEntry<V>> extends RegistryAPI<V> {

    public Registry1_12_2(IForgeRegistry<V> forgeRegistry, Class<V> type, ResourceLocation1_12_2 registryKey) {
        super(forgeRegistry,type,registryKey);
    }
    
    @SuppressWarnings("unchecked")
    @Override public IForgeRegistry<V> getBackend() {
        return super.getBackend();
    }

    @Override public ResourceLocationAPI<?> getKey(V value) {
        return WrapperHelper.wrapResourceLocation(getBackend().getKey(value));
    }

    @Override public V getValue(ResourceLocationAPI<?> key) {
        return getBackend().getValue(key.unwrap());
    }

    @Override public boolean hasKey(ResourceLocationAPI<?> key) {
        return getBackend().containsKey(key.unwrap());
    }

    @Override public boolean hasValue(V value) {
        return getBackend().containsValue(value);
    }
}