package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m4.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.core.TILCoreForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m4.client.ClientForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m4.common.CommonForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.common.TILCommonEntryPoint1_20_4;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_4;

@IndirectCallers
public class TILCoreForge1_20_4 extends TILCoreForge1_20 {
    
    public TILCoreForge1_20_4() {
        super(V20_4);
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return TILCommonEntryPoint1_20_4.getInstance();
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientForge1_20_4() : new CommonForge1_20_4());
    }
}