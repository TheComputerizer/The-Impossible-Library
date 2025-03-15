package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.common;

import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.TILCommonEntryPointFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.server.WrappedCommand1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.tab.CreativeTabBuilder1_20;

import java.util.Objects;

public class TILCommonEntryPointFabric1_20_6 extends TILCommonEntryPointFabric1_20 {
    
    private static TILCommonEntryPointFabric1_20_6 INSTANCE;
    
    public static TILCommonEntryPointFabric1_20_6 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointFabric1_20_6();
    }
    
    private TILCommonEntryPointFabric1_20_6() {
        INSTANCE = this;
    }
    
    @Override public void onLoadComplete() {
        FabricHelper.registerServerHooks();
        WrappedCommand1_20_6.registerArgType();
        CreativeTabBuilder1_20.onRegister(null);
        super.onLoadComplete();
    }
}
