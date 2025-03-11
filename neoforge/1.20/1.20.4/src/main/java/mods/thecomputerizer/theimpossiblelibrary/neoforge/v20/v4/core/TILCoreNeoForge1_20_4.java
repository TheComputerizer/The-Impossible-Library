package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v4.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.core.TILCoreNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v4.client.ClientNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v4.common.CommonNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.common.TILCommonEntryPoint1_20_4;

@IndirectCallers
public class TILCoreNeoForge1_20_4 extends TILCoreNeoForge1_20 {
    
    public TILCoreNeoForge1_20_4() {
        super(true);
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return new TILCommonEntryPoint1_20_4();
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientNeoForge1_20_4() : new CommonNeoForge1_20_4());
    }
}
