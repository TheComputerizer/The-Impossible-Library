package mods.thecomputerizer.theimpossiblelibrary.shared.v19.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.server.WrappedCommand1_19;
import org.jetbrains.annotations.Nullable;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

public class TILCommonEntryPoint1_19 extends CommonEntryPoint {
    
    @Override public @Nullable ClientEntryPoint delegatedClientEntry() {
        return null;
    }
    
    @Override protected String getModID() {
        return MODID;
    }
    
    @Override protected String getModName() {
        return NAME;
    }
    
    @Override public void onLoadComplete() {
        WrappedCommand1_19.registerArgType();
    }
}
