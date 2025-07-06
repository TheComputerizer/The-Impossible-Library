package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.DelegatingCommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server.WrappedCommand1_16_5;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

public class TILCommonEntryPoint1_16_5 extends DelegatingCommonEntryPoint {
    
    private static TILCommonEntryPoint1_16_5 INSTANCE;
    
    public static TILCommonEntryPoint1_16_5 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPoint1_16_5();
    }
    
    protected TILCommonEntryPoint1_16_5() {
        INSTANCE = this;
    }
    
    @Override protected String getModID() {
        return MODID;
    }
    
    @Override protected String getModName() {
        return NAME;
    }
    
    @Override public void onLoadComplete() {
        WrappedCommand1_16_5.registerArgType();
        super.onLoadComplete();
    }
}
