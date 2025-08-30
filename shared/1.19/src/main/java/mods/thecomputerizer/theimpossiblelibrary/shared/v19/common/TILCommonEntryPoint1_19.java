package mods.thecomputerizer.theimpossiblelibrary.shared.v19.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.DelegatingCommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.server.WrappedCommand1_19;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

public abstract class TILCommonEntryPoint1_19 extends DelegatingCommonEntryPoint {
    
    protected TILCommonEntryPoint1_19() {}
    
    @Override protected String getModID() {
        return MODID;
    }
    
    @Override protected String getModName() {
        return NAME;
    }
    
    @Override public void onLoadComplete() {
        WrappedCommand1_19.registerArgType();
        super.onLoadComplete();
    }
}
