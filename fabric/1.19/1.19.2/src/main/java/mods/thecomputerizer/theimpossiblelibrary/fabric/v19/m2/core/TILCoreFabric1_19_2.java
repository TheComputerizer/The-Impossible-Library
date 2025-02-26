package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.core.TILCoreFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m2.client.ClientFabric1_19_2;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m2.common.CommonFabric1_19_2;

@IndirectCallers
public class TILCoreFabric1_19_2 extends TILCoreFabric1_19 {
    
    public TILCoreFabric1_19_2() {
        super(true);
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientFabric1_19_2() : new CommonFabric1_19_2());
    }
}
