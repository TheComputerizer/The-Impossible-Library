package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.test.ClientTests.TEST_KEY;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

/**
 * For internal use only
 */
public final class TILClientEntryPoint extends DelegatingClientEntryPoint {

    private static TILClientEntryPoint INSTANCE;
    
    private static void devTrace(String msg, Object ... args) {
        TILRef.logInfo("[TILClientEntryPoint Trace]: "+msg,args);
    }

    public static TILClientEntryPoint getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILClientEntryPoint();
    }

    public static void init() {
        devTrace("init");
        getInstance().onClientSetup();
    }

    private TILClientEntryPoint() {
        devTrace("constructor");
        INSTANCE = this;
    }

    @Override protected String getModID() {
        return MODID;
    }

    @Override protected String getModName() {
        return NAME;
    }
    
    @Override public void onPreRegistration() {
        devTrace("onPreRegistration");
        EventHelper.initTILListeners(true,DEV);
        super.onPreRegistration();
    }
    
    @Override public void onClientSetup() {
        devTrace("onClientSetup");
        if(DEV) KeyHelper.register(TEST_KEY);
    }
    
    @Override public void onLoadComplete() {
        devTrace("onLoadComplete");
        super.onLoadComplete();
        TILRef.getClientHandles().onFinishedLoading();
    }
    
    public CommonEntryPoint setDelegatedCustomHandle() {
        return CoreAPI.getInstance().getClientVersionHandler();
    }
}