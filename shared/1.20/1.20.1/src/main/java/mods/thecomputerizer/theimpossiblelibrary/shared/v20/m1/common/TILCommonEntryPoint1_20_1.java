package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m1.common;

import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.TILCommonEntryPoint1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m1.server.WrappedCommand1_20_1;

import java.util.Objects;

public class TILCommonEntryPoint1_20_1 extends TILCommonEntryPoint1_20 {
    
    private static TILCommonEntryPoint1_20_1 INSTANCE;
    
    public static TILCommonEntryPoint1_20_1 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPoint1_20_1();
    }
    
    private TILCommonEntryPoint1_20_1() {
        INSTANCE = this;
    }
    
    @Override public void onLoadComplete() {
        WrappedCommand1_20_1.registerArgType();
    }
}
