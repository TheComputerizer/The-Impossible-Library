package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.common;

import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.TILCommonEntryPoint1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.server.WrappedCommand1_20_4;

import java.util.Objects;

public class TILCommonEntryPoint1_20_4 extends TILCommonEntryPoint1_20 {
    
    private static TILCommonEntryPoint1_20_4 INSTANCE;
    
    public static TILCommonEntryPoint1_20_4 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPoint1_20_4();
    }
    
    protected TILCommonEntryPoint1_20_4() {
        INSTANCE = this;
    }
    
    @Override public void onLoadComplete() {
        WrappedCommand1_20_4.registerArgType();
        super.onLoadComplete();
    }
}
