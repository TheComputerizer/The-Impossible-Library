package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.Registry1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryFabric1_16_5<V extends IForgeRegistryEntry<V>> extends Registry1_16_5<V> {

    private final IForgeRegistry<V> forgeRegistry;

    public RegistryFabric1_16_5(IForgeRegistry<V> forgeRegistry, ResourceLocation1_16_5 registryKey, Class<V> type) {
        super(type,registryKey);
        this.forgeRegistry = forgeRegistry;
    }

    @Override
    public ResourceLocation1_16_5 getKey(V value) {
        return new ResourceLocation1_16_5(this.forgeRegistry.getKey(value));
    }

    @Override
    public V getValue(ResourceLocationAPI<?> key) {
        return this.forgeRegistry.getValue(((ResourceLocation1_16_5)key).getInstance());
    }

    @Override
    public boolean hasKey(ResourceLocationAPI<?> key) {
        return this.forgeRegistry.containsKey(((ResourceLocation1_16_5)key).getInstance());
    }

    @Override
    public boolean hasValue(V value) {
        return this.forgeRegistry.containsValue(value);
    }
}
