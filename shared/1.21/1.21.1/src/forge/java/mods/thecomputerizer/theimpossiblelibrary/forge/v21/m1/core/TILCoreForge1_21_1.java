package mods.thecomputerizer.theimpossiblelibrary.forge.v21.m1.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.forge.v21.core.TILCoreForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.forge.v21.m1.client.ClientForge1_21_1;
import mods.thecomputerizer.theimpossiblelibrary.forge.v21.m1.common.CommonForge1_21_1;
import mods.thecomputerizer.theimpossiblelibrary.forge.v21.m1.common.TILCommonEntryPointForge1_21_1;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V21_1;

@IndirectCallers
public class TILCoreForge1_21_1 extends TILCoreForge1_21 {
    
    public TILCoreForge1_21_1() {
        super(V21_1);
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return TILCommonEntryPointForge1_21_1.getInstance();
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientForge1_21_1() : new CommonForge1_21_1());
    }
}