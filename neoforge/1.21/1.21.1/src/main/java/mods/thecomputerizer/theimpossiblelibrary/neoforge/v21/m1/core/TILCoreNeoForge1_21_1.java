package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.m1.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core.TILCoreNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.m1.client.ClientNeoForge1_21_1;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.m1.common.CommonNeoForge1_21_1;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V21_1;

@IndirectCallers
public class TILCoreNeoForge1_21_1 extends TILCoreNeoForge1_21 {
    
    public TILCoreNeoForge1_21_1() {
        super(V21_1);
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientNeoForge1_21_1() : new CommonNeoForge1_21_1());
    }
}
