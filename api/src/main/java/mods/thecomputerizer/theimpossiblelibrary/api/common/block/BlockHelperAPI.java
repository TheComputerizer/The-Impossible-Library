package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

public interface BlockHelperAPI {
    
    <V extends Comparable<V>> BlockPropertyAPI<?,V> createProperty(String name, V defVal);
    <P> BlockPropertyAPI<?,?> getAsProperty(P property);
}