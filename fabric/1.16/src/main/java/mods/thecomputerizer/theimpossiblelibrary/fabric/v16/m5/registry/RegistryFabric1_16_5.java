package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.Registry1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class RegistryFabric1_16_5<V> extends Registry1_16_5<V> {

    private final Registry<V> registry;

    public RegistryFabric1_16_5(Registry<V> registry, ResourceLocation1_16_5 registryKey, Class<V> type) {
        super(type,registryKey);
        this.registry = registry;
    }

    @Override
    public ResourceLocation1_16_5 getKey(V value) {
        return new ResourceLocation1_16_5(this.registry.getKey(value));
    }

    @Override
    public V getValue(ResourceLocationAPI<?> key) {
        return this.registry.get((ResourceLocation)key.getInstance());
    }

    @Override
    public boolean hasKey(ResourceLocationAPI<?> key) {
        return this.registry.containsKey((ResourceLocation)key.getInstance());
    }

    @Override
    public boolean hasValue(V value) {
        return Objects.nonNull(this.registry.getKey(value));
    }
}
