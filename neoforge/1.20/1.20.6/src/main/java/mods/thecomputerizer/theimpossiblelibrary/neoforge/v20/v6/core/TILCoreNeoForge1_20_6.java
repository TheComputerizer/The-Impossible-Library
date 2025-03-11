package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v6.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core.TILCoreNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v6.client.ClientNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v6.common.CommonNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.common.TILCommonEntryPoint1_20_6;

@IndirectCallers
public class TILCoreNeoForge1_20_6 extends TILCoreNeoForge1_20 {
    
    public TILCoreNeoForge1_20_6() {
        super(false);
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return new TILCommonEntryPoint1_20_6();
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientNeoForge1_20_6() : new CommonNeoForge1_20_6());
    }
}
