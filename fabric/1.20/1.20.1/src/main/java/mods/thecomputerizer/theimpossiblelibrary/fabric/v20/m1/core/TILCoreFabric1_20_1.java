package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.core.TILCoreFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.client.ClientFabric1_20_1;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.common.CommonFabric1_20_1;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.common.TILCommonEntryPointFabric1_20_1;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_1;

@IndirectCallers
public class TILCoreFabric1_20_1 extends TILCoreFabric1_20 {
    
    public TILCoreFabric1_20_1() {
        super(V20_1);
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return new TILCommonEntryPointFabric1_20_1();
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientFabric1_20_1() : new CommonFabric1_20_1());
    }
}