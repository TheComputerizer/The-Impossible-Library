package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.test.ClientTests.TEST_KEY;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

/**
 * For internal use only
 */
public final class TILClientEntryPoint extends ClientEntryPoint {

    private static TILClientEntryPoint INSTANCE;
    
    private static void devTrace(String msg, Object ... args) {
        TILDev.logDebug("[TILClientEntryPoint Trace]: "+msg,args);
    }

    public static TILClientEntryPoint getInstance() {
        if(Objects.isNull(INSTANCE)) INSTANCE = new TILClientEntryPoint();
        return INSTANCE;
    }

    public static void init() {
        devTrace("init");
        if(Objects.nonNull(INSTANCE)) INSTANCE.onClientSetup();
    }

    private final ClientEntryPoint versionHandler;

    public TILClientEntryPoint() {
        devTrace("constructor");
        CommonEntryPoint versionHandler = CoreAPI.getInstance().getClientVersionHandler();
        this.versionHandler = versionHandler instanceof ClientEntryPoint ? (ClientEntryPoint)versionHandler : null;
    }

    @Override public ClientEntryPoint delegatedClientEntry() {
        return this;
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
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onPreRegistration();
    }

    @Override public void onClientSetup() {
        devTrace("onClientSetup");
        if(DEV) KeyHelper.register(TEST_KEY);
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onClientSetup();
    }
}