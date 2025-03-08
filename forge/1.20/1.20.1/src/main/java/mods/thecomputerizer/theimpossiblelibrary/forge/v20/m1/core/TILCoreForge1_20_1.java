package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m1.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.core.TILCoreForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m1.client.ClientForge1_20_1;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m1.common.CommonForge1_20_1;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_1;

@IndirectCallers
public class TILCoreForge1_20_1 extends TILCoreForge1_20 {
    
    public TILCoreForge1_20_1() {
        super(V20_1);
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientForge1_20_1() : new CommonForge1_20_1());
    }
}