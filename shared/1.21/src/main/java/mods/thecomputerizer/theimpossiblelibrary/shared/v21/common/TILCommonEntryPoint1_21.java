package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.DelegatingCommonEntryPoint;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

public abstract class TILCommonEntryPoint1_21 extends DelegatingCommonEntryPoint {
    
    @Override protected String getModID() {
        return MODID;
    }
    
    @Override protected String getModName() {
        return NAME;
    }
}
