package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.Wrapped;

import java.util.Objects;
import java.util.StringJoiner;

public interface RegistryEntryAPI<V> extends Wrapped<V> {
    
    boolean FORGE = CoreAPI.isForge();
    boolean FORGE_OR_NEOFORGE = FORGE || CoreAPI.isNeoforge();
    boolean NAMED_ENV = CoreAPI.isNamedEnv();
    boolean SRG_ENV = CoreAPI.isSrgEnv();
    
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
        return RegistryHelper.getRegistry(getWrappedClass());
    }
    
    default ResourceLocationAPI<?> getRegistryName() {
        return getRegistry().getKey(unwrap());
    }
    
    default ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world) {
        return getRegistryName();
    }
    
    void setRegistryName(ResourceLocationAPI<?> registryName);
}