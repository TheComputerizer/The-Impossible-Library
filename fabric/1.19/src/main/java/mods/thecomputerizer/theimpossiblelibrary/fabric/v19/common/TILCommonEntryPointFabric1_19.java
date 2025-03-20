package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.TILCommonEntryPointFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;

public abstract class TILCommonEntryPointFabric1_19 extends TILCommonEntryPointFabric {
    
    protected TILCommonEntryPointFabric1_19() {}
    
    @Override public void onLoadComplete() {
        FabricHelper.registerServerHooks();
        super.onLoadComplete();
    }
}