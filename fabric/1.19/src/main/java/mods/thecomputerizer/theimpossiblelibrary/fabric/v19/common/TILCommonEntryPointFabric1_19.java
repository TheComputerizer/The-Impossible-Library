package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.TILCommonEntryPointFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.server.WrappedCommand1_19;

public abstract class TILCommonEntryPointFabric1_19 extends TILCommonEntryPointFabric {
    
    protected TILCommonEntryPointFabric1_19() {}
    
    @Override public void onLoadComplete() {
        FabricHelper.registerServerHooks();
        WrappedCommand1_19.registerArgType();
        super.onLoadComplete();
    }
}