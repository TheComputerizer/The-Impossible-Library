package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import java.util.Objects;

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
        TILRef.logInfo("TIL CLIENT CONSTRUCTOR");
        CommonEntryPoint versionHandler = CoreAPI.INSTANCE.getClientVersionHandler();
        this.versionHandler = versionHandler instanceof ClientEntryPoint ? (ClientEntryPoint)versionHandler : null;
    }

    @Override
    public void onClientSetup() { //TODO Abstract tests and keybind registration
        TILRef.logInfo("TIL CLIENT SETUP");
        EventHelper.initTILListeners(true,true);
        //ClientTests1_12_2.initClientTests();
        //ClientRegistry.registerKeyBinding(ClientTests1_12_2.TEST_KEYBIND);
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onClientSetup();
    }
}