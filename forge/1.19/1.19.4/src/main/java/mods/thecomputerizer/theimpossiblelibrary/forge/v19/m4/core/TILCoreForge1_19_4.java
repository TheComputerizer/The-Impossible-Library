package mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.core.TILCoreForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.client.ClientForge1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.common.CommonForge1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.common.TILCommonEntryPointForge1_19_4;

@IndirectCallers
public class TILCoreForge1_19_4 extends TILCoreForge1_19 {
    
    public TILCoreForge1_19_4() {
        super(false);
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return TILCommonEntryPointForge1_19_4.getInstance();
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientForge1_19_4() : new CommonForge1_19_4());
    }
}