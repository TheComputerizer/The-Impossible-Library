package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.core.TILCoreForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client.ClientForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.common.CommonForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.common.TILCommonEntryPoint1_20_6;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_6;

@IndirectCallers
public class TILCoreForge1_20_6 extends TILCoreForge1_20 {
    
    public TILCoreForge1_20_6() {
        super(V20_6);
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return new TILCommonEntryPoint1_20_6();
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientForge1_20_6() : new CommonForge1_20_6());
    }
}