package mods.thecomputerizer.theimpossiblelibrary.api.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;

public abstract class ItemRegistryHelperAPI {
    
    public ItemStackAPI<?> wrapStack(Object stack) {
        return WrapperHelper.wrapItemStack(stack);
    }
    
    public WorldAPI<?> wrapWorld(Object world) {
        return WrapperHelper.wrapWorld(world);
    }
}