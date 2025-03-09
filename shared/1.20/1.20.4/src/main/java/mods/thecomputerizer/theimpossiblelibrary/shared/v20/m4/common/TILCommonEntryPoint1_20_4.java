package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.common;

import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.TILCommonEntryPoint1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.server.WrappedCommand1_20_4;

public class TILCommonEntryPoint1_20_4 extends TILCommonEntryPoint1_20 {
    
    @Override public void onLoadComplete() {
        WrappedCommand1_20_4.registerArgType();
    }
}
