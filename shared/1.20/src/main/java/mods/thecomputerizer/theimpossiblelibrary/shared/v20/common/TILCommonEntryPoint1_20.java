package mods.thecomputerizer.theimpossiblelibrary.shared.v20.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import org.jetbrains.annotations.Nullable;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

public abstract class TILCommonEntryPoint1_20 extends CommonEntryPoint {
    
    @Override public @Nullable ClientEntryPoint delegatedClientEntry() {
        return null;
    }
    
    @Override protected String getModID() {
        return MODID;
    }
    
    @Override protected String getModName() {
        return NAME;
    }
}
