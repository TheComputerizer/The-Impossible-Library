package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.core.TILCoreFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.client.ClientFabric1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.common.CommonFabric1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.common.TILCommonEntryPointFabric1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.core.asm.ModWriterFabric1_20_6;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_6;

@IndirectCallers
public class TILCoreFabric1_20_6 extends TILCoreFabric1_20 {
    
    public TILCoreFabric1_20_6() {
        super(V20_6);
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return TILCommonEntryPointFabric1_20_6.getInstance();
    }
    
    @Override protected ModWriter getModWriter(MultiVersionModInfo info) {
        return new ModWriterFabric1_20_6(this,info);
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientFabric1_20_6() : new CommonFabric1_20_6());
    }
}