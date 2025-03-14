package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m4.common;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.TILCommonEntryPointFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.server.WrappedCommand1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.tab.CreativeTabBuilder1_20;

import java.util.Objects;

public class TILCommonEntryPointFabric1_20_4 extends TILCommonEntryPointFabric {
    
    private static TILCommonEntryPointFabric1_20_4 INSTANCE;
    
    public static TILCommonEntryPointFabric1_20_4 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointFabric1_20_4();
    }
    
    private TILCommonEntryPointFabric1_20_4() {
        INSTANCE = this;
    }
    
    @Override public void onLoadComplete() {
        FabricHelper.registerServerHooks();
        WrappedCommand1_20_4.registerArgType();
        CreativeTabBuilder1_20.onRegister(null);
        super.onLoadComplete();
    }
}
