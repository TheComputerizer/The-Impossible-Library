package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.common;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.TILCommonEntryPointFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.server.WrappedCommand1_20_6;

public class TILCommonEntryPointFabric1_20_6 extends TILCommonEntryPointFabric {
    
    @Override public void onLoadComplete() {
        FabricHelper.registerServerHooks();
        WrappedCommand1_20_6.registerArgType();
    }
}
