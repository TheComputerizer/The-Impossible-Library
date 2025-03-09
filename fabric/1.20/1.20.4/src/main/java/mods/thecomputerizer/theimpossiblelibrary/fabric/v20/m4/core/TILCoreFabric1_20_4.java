package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m4.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.core.TILCoreFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m4.client.ClientFabric1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m4.common.CommonFabric1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m4.common.TILCommonEntryPointFabric1_20_4;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_4;

@IndirectCallers
public class TILCoreFabric1_20_4 extends TILCoreFabric1_20 {
    
    public TILCoreFabric1_20_4() {
        super(V20_4);
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return new TILCommonEntryPointFabric1_20_4();
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientFabric1_20_4() : new CommonFabric1_20_4());
    }
}