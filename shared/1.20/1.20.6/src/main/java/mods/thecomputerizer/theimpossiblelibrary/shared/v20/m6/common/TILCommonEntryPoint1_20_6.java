package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.common;

import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.TILCommonEntryPoint1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.server.WrappedCommand1_20_6;

import java.util.Objects;

public class TILCommonEntryPoint1_20_6 extends TILCommonEntryPoint1_20 {
    
    private static TILCommonEntryPoint1_20_6 INSTANCE;
    
    public static TILCommonEntryPoint1_20_6 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPoint1_20_6();
    }
    
    protected TILCommonEntryPoint1_20_6() {
        INSTANCE = this;
    }
    
    @Override public void onLoadComplete() {
        WrappedCommand1_20_6.registerArgType();
        super.onLoadComplete();
    }
}