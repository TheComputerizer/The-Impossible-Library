package mods.thecomputerizer.theimpossiblelibrary.api.server;

import mods.thecomputerizer.theimpossiblelibrary.api.common.DelegatingCommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

public class TILServerEntryPoint extends DelegatingCommonEntryPoint {
    
    private static TILServerEntryPoint INSTANCE;
    
    public static TILServerEntryPoint getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILServerEntryPoint();
    }
    
    private static void devTrace(String msg, Object ... args) {
        TILRef.logInfo("[TILServerEntryPoint Trace]: "+msg, args);
    }
    
    private TILServerEntryPoint() {
        INSTANCE = this;
    }
    
    @Override protected String getModID() {
        return MODID;
    }
    
    @Override protected String getModName() {
        return NAME;
    }
    
    @Override protected void onDedicatedServerSetup() {
        devTrace("onDedicatedServerSetup");
        EventHelper.initTILListeners(false,false,true,DEV);
    }
}
