package mods.thecomputerizer.theimpossiblelibrary.api.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

public class ItemHelper {
    
    public static ToolHelperAPI getToolHelper() {
        return TILRef.getCommonSubAPI(CommonAPI::getToolHelper);
    }
    
    public static ToolTierAPI<?> getToolTier(String name) {
        return getToolHelper().getTier(name);
    }
}