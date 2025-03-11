package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.TILCommonEntryPointFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.server.WrappedCommand1_19;

import java.util.Objects;

public class TILCommonEntryPointFabric1_19 extends TILCommonEntryPointFabric {
    
    private static TILCommonEntryPointFabric1_19 INSTANCE;
    
    public static TILCommonEntryPointFabric1_19 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointFabric1_19();
    }
    
    private TILCommonEntryPointFabric1_19() {
        INSTANCE = this;
    }
    
    @Override public void onLoadComplete() {
        FabricHelper.registerServerHooks();
        WrappedCommand1_19.registerArgType();
    }
}