package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.core.TILCoreFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.client.ClientFabric1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.common.CommonFabric1_19_4;

@IndirectCallers
public class TILCoreFabric1_19_4 extends TILCoreFabric1_19 {
    
    public TILCoreFabric1_19_4() {
        super(false);
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientFabric1_19_4() : new CommonFabric1_19_4());
    }
}
