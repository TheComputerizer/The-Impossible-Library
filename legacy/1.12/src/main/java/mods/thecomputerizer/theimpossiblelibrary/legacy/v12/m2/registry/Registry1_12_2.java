package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Getter
public class Registry1_12_2<V extends IForgeRegistryEntry<V>> extends RegistryAPI<V> {

    private final IForgeRegistry<V> forgeRegistry;

    public Registry1_12_2(IForgeRegistry<V> forgeRegistry, Class<V> type, ResourceLocation1_12_2 registryKey) {
        super(type,registryKey);
        this.forgeRegistry = forgeRegistry;
    }

    @Override
    public ResourceLocationAPI<?> getKey(V value) {
        return new ResourceLocation1_12_2(this.forgeRegistry.getKey(value));
    }

    @Override
    public V getValue(ResourceLocationAPI<?> key) {
        return this.forgeRegistry.getValue(((ResourceLocation1_12_2)key).getInstance());
    }

    @Override
    public boolean hasKey(ResourceLocationAPI<?> key) {
        return this.forgeRegistry.containsKey(((ResourceLocation1_12_2)key).getInstance());
    }

    @Override
    public boolean hasValue(V value) {
        return this.forgeRegistry.containsValue(value);
    }
}
