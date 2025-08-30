package mods.thecomputerizer.theimpossiblelibrary.forge.v19.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.core.TILCoreForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m2.client.ClientForge1_19_2;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m2.common.CommonForge1_19_2;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m2.common.TILCommonEntryPointForge1_19_2;

@IndirectCallers
public class TILCoreForge1_19_2 extends TILCoreForge1_19 {
    
    public TILCoreForge1_19_2() {
        super(true);
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return TILCommonEntryPointForge1_19_2.getInstance();
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientForge1_19_2() : new CommonForge1_19_2());
    }
}