package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import java.util.Objects;

/**
 * For internal use only. Used to distribute client side entrypoint states.
 */
public final class ClientEntryPointDistributor extends ClientEntryPoint {

    private static ClientEntryPointDistributor INSTANCE;

    public static void init() {
        if(Objects.nonNull(INSTANCE)) INSTANCE.onClientSetup();
    }

    public ClientEntryPointDistributor() {
        TILRef.logInfo("TIL CLIENT CONSTRUCTOR");
        INSTANCE = this;
    }

    @Override
    public void onClientSetup() { //TODO Abstract tests and keybind registration
        TILRef.logInfo("TIL CLIENT SETUP");
        EventHelper.initTILListeners(true,true);
        //ClientTests1_12_2.initClientTests();
        //ClientRegistry.registerKeyBinding(ClientTests1_12_2.TEST_KEYBIND);
        for(CommonEntryPoint entryPoint : CoreAPI.INSTANCE.getModInstances())
            if(entryPoint instanceof ClientEntryPoint) ((ClientEntryPoint)entryPoint).onClientSetup();
    }
}