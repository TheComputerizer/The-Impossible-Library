package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.DelegatingCommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.server.WrappedCommand1_18_2;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

public class TILCommonEntryPoint1_18_2 extends DelegatingCommonEntryPoint {
    
    private static TILCommonEntryPoint1_18_2 INSTANCE;
    
    public static TILCommonEntryPoint1_18_2 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPoint1_18_2();
    }
    
    protected TILCommonEntryPoint1_18_2() {
        INSTANCE = this;
    }
    
    @Override protected String getModID() {
        return MODID;
    }
    
    @Override protected String getModName() {
        return NAME;
    }
    
    @Override public void onLoadComplete() {
        WrappedCommand1_18_2.registerArgType();
        super.onLoadComplete();
    }
}
