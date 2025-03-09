package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.common;

import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.TILCommonEntryPoint1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.server.WrappedCommand1_20_6;

public class TILCommonEntryPoint1_20_6 extends TILCommonEntryPoint1_20 {
    
    @Override public void onLoadComplete() {
        WrappedCommand1_20_6.registerArgType();
    }
}