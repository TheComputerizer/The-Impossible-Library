package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.test.ClientTests.TEST_KEY;

/**
 * For internal use only
 */
public final class TILClientEntryPoint extends ClientEntryPoint {

    private static TILClientEntryPoint INSTANCE;

    public static TILClientEntryPoint getInstance() {
        if(Objects.isNull(INSTANCE)) INSTANCE = new TILClientEntryPoint();
        return INSTANCE;
    }

    public static void init() {
        if(Objects.nonNull(INSTANCE)) INSTANCE.onClientSetup();
    }

    private final ClientEntryPoint versionHandler;

    public TILClientEntryPoint() {
        TILDev.logInfo("TIL CLIENT CONSTRUCTOR");
        CommonEntryPoint versionHandler = CoreAPI.INSTANCE.getClientVersionHandler();
        this.versionHandler = versionHandler instanceof ClientEntryPoint ? (ClientEntryPoint)versionHandler : null;
    }

    @Override
    public ClientEntryPoint delegatedClientEntry() {
        return this;
    }

    @Override
    protected String getModID() {
        return TILRef.MODID;
    }

    @Override
    protected String getModName() {
        return TILRef.NAME;
    }

    @Override
    public void onPreRegistration() {
        TILDev.logInfo("TIL CLIENT PRE REGISTRATION");
        EventHelper.initTILListeners(true,TILDev.DEV);
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onPreRegistration();
    }

    @Override
    public void onClientSetup() {
        TILDev.logInfo("TIL CLIENT SETUP");
        KeyHelper.register(TEST_KEY);
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onClientSetup();
    }
}