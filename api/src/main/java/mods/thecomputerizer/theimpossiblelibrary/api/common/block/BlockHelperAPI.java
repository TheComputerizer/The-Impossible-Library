package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;

import java.util.Objects;

public interface BlockHelperAPI {
    
    <V extends Comparable<V>> BlockPropertyAPI<?,V> createProperty(String name, V defVal);
    <P> BlockPropertyAPI<?,?> getAsProperty(P property);
    
    default MaterialAPI<?> getMaterialByName(String name) {
        Object material = getMaterialByNameDirect(name);
        return Objects.nonNull(material) ? WrapperHelper.wrapMaterial(material) : null;
    }
    
    Object getMaterialByNameDirect(String name);
    MaterialColorAPI<?> getMaterialColorByName(String name);
}