package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

@SuppressWarnings("unused") public class BlockHelper {
    
    public static <V extends Comparable<V>> BlockPropertyAPI<?,V> createProperty(String name, V defVal) {
        return getAPI().createProperty(name,defVal);
    }
    
    public static <P> BlockPropertyAPI<?,?> getAsProperty(P property) {
        return getAPI().getAsProperty(property);
    }
    
    public static BlockHelperAPI getAPI() {
        return TILRef.getCommonSubAPI(CommonAPI::getBlockHelper);
    }
}