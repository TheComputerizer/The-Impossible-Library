package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

public class TILCoreEntryPointForge1_16_5 extends CoreEntryPoint {
    
    public TILCoreEntryPointForge1_16_5() {
        TILRef.logInfo("Initialized core version handler {}",getClass());
    }
    
    @Override public String getCoreID() {
        return MODID+"_core";
    }
    
    @Override public String getCoreName() {
        return NAME+" Core";
    }
}