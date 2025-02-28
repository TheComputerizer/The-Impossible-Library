package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.TILCommonEntryPointFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.server.WrappedCommand1_19;

public class TILCommonEntryPointFabric1_19 extends TILCommonEntryPointFabric {
    
    @Override public void onLoadComplete() {
        FabricHelper.registerServerHooks();
        WrappedCommand1_19.registerArgType();
    }
}