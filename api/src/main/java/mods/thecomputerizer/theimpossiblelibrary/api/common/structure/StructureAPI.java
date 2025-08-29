package mods.thecomputerizer.theimpossiblelibrary.api.common.structure;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

import java.util.Objects;
import java.util.StringJoiner;

public abstract class StructureAPI<S> extends AbstractWrapped<S> {

    protected StructureAPI(Object structure) {
        super(structure);
    }
    
    @IndirectCallers public String getName() {
        ResourceLocationAPI<?> registryName = getRegistryName();
        if(Objects.isNull(registryName)) return null;
        String[] words = registryName.getPath().split("_");
        StringJoiner joiner = new StringJoiner(" ");
        for(String word : words) joiner.add(TextHelper.capitalize(word));
        return joiner.toString();
    }
    
    @IndirectCallers public String getName(WorldAPI<?> world) {
        ResourceLocationAPI<?> registryName = getRegistryName(world);
        if(Objects.isNull(registryName)) return null;
        String[] words = registryName.getPath().split("_");
        StringJoiner joiner = new StringJoiner(" ");
        for(String word : words) joiner.add(TextHelper.capitalize(word));
        return joiner.toString();
    }
    
    public abstract ResourceLocationAPI<?> getRegistryName();
    
    public ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world) {
        return getRegistryName();
    }
}