package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.m1.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.core.TILCoreFabric1_21;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.m1.client.ClientFabric1_21_1;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.m1.common.CommonFabric1_21_1;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V21_1;

@IndirectCallers
public class TILCoreFabric1_21_1 extends TILCoreFabric1_21 {
    
    public TILCoreFabric1_21_1() {
        super(V21_1);
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientFabric1_21_1() : new CommonFabric1_21_1());
    }
}