package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.Wrapped;

import java.util.Objects;
import java.util.StringJoiner;

public interface RegistryEntryAPI<V> extends Wrapped<V> {
    
    @IndirectCallers default String getName() {
        ResourceLocationAPI<?> registryName = getRegistryName();
        if(Objects.isNull(registryName)) return null;
        String[] words = registryName.getPath().split("_");
        StringJoiner joiner = new StringJoiner(" ");
        for(String word : words) joiner.add(TextHelper.capitalize(word));
        return joiner.toString();
    }
    
    @IndirectCallers default String getName(WorldAPI<?> world) {
        ResourceLocationAPI<?> registryName = getRegistryName(world);
        if(Objects.isNull(registryName)) return null;
        String[] words = registryName.getPath().split("_");
        StringJoiner joiner = new StringJoiner(" ");
        for(String word : words) joiner.add(TextHelper.capitalize(word));
        return joiner.toString();
    }

    default RegistryAPI<?> getRegistry() {
        Class<?> wrappedClass = getWrappedClass();
        return Objects.nonNull(wrappedClass) ? RegistryHelper.getRegistry(wrappedClass) : null;
    }
    
    default ResourceLocationAPI<?> getRegistryName() {
        RegistryAPI<?> registry = getRegistry();
        return Objects.nonNull(registry) && Objects.nonNull(getWrapped()) ? registry.getKey(unwrap()) : null;
    }
    
    default ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world) {
        return getRegistryName();
    }
    
    void setRegistryName(ResourceLocationAPI<?> registryName);
}