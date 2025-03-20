package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.DelegatingCommonEntryPoint;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

public class TILCommonEntryPoint1_21 extends DelegatingCommonEntryPoint {
    
    private static TILCommonEntryPoint1_21 INSTANCE;
    
    public static TILCommonEntryPoint1_21 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPoint1_21();
    }
    
    protected TILCommonEntryPoint1_21() {
        INSTANCE = this;
    }
    
    @Override protected String getModID() {
        return MODID;
    }
    
    @Override protected String getModName() {
        return NAME;
    }
}
