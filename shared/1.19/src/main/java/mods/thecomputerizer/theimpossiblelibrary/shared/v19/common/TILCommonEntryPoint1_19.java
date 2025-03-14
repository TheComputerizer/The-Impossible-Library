package mods.thecomputerizer.theimpossiblelibrary.shared.v19.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.DelegatingCommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.server.WrappedCommand1_19;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

public class TILCommonEntryPoint1_19 extends DelegatingCommonEntryPoint {
    
    private static TILCommonEntryPoint1_19 INSTANCE;
    
    public static TILCommonEntryPoint1_19 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPoint1_19();
    }
    
    protected TILCommonEntryPoint1_19() {
        INSTANCE = this;
    }
    
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
