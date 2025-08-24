package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ModWriter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.core.TILCoreForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client.ClientForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.common.CommonForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.common.TILCommonEntryPointForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.core.asm.ModWriterForge1_20_6;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V20_6;

@IndirectCallers
public class TILCoreForge1_20_6 extends TILCoreForge1_20 {
    
    public TILCoreForge1_20_6() {
        super(V20_6);
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return TILCommonEntryPointForge1_20_6.getInstance();
    }
    
    @Override protected ModWriter getModWriter(MultiVersionModInfo info) {
        return new ModWriterForge1_20_6(this,info);
    }
    
    @Override public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new ClientForge1_20_6() : new CommonForge1_20_6());
    }
}