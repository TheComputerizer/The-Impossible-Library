package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.common;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.TILCommonEntryPointFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m1.server.WrappedCommand1_20_1;

public class TILCommonEntryPointFabric1_20_1 extends TILCommonEntryPointFabric {
    
    @Override public void onLoadComplete() {
        FabricHelper.registerServerHooks();
        WrappedCommand1_20_1.registerArgType();
    }
}
