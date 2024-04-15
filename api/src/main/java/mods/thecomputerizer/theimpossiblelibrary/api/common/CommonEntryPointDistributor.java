package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPointDistributor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHandler;

/**
 * For internal use only. Used to distribute entrypoint states.
 */
public final class CommonEntryPointDistributor extends CommonEntryPoint {

    public static CommonEntryPointDistributor INSTANCE;

    public CommonEntryPointDistributor() {
        TILRef.logInfo("TIL COMMON CONSTRUCTOR");
        if(CoreAPI.INSTANCE.getSide().isClient()) new ClientEntryPointDistributor();
        INSTANCE = this;
    }

    @Override
    public void onConstructed() {
        TILRef.logInfo("TIL COMMON CONSTRUCTED");
        distributeStates(CoreAPI.INSTANCE.getModInstances(),CommonEntryPoint::onConstructed);
    }

    @Override
    public void onPreRegistration() {
        TILRef.logInfo("TIL COMMON PRE REGISTRATION");
        distributeStates(CoreAPI.INSTANCE.getModInstances(),CommonEntryPoint::onPreRegistration);
    }

    @Override
    public void onCommonSetup() {
        TILRef.logInfo("TIL COMMON SETUP");
        NetworkHandler.load();
        distributeStates(CoreAPI.INSTANCE.getModInstances(),CommonEntryPoint::onCommonSetup);
    }

    @Override
    public void onDedicatedServerSetup() {
        TILRef.logInfo("TIL DEDICATED SERVER SETUP");
        if(CoreAPI.INSTANCE.getSide().isServer())
            for(CommonEntryPoint entryPoint : CoreAPI.INSTANCE.getModInstances())
                if(!(entryPoint instanceof ClientEntryPoint)) entryPoint.onDedicatedServerSetup();
    }

    @Override
    public void onInterModEnqueue() {
        TILRef.logInfo("TIL INTER MOD ENQUEUE");
        distributeStates(CoreAPI.INSTANCE.getModInstances(),CommonEntryPoint::onInterModEnqueue);
    }

    @Override
    public void onInterModProcess() {
        TILRef.logInfo("TIL INTER MOD PROCESS");
        distributeStates(CoreAPI.INSTANCE.getModInstances(),CommonEntryPoint::onInterModProcess);
    }

    @Override
    public void onLoadComplete() {
        TILRef.logInfo("TIL LOAD COMPLETE");
        distributeStates(CoreAPI.INSTANCE.getModInstances(),CommonEntryPoint::onLoadComplete);
    }

    @Override
    public void onServerAboutToStart() {
        TILRef.logInfo("TIL SERVER ABOUT TO START");
        distributeStates(CoreAPI.INSTANCE.getModInstances(),CommonEntryPoint::onServerAboutToStart);
    }

    @Override
    public void onServerStarting() {
        TILRef.logInfo("TIL SERVER STARTING");
        distributeStates(CoreAPI.INSTANCE.getModInstances(),CommonEntryPoint::onServerStarting);
    }

    @Override
    public void onServerStarted() {
        TILRef.logInfo("TIL SERVER STARTED");
        distributeStates(CoreAPI.INSTANCE.getModInstances(),CommonEntryPoint::onServerStarted);
    }

    @Override
    public void onServerStopping() {
        TILRef.logInfo("TIL SERVER STOPPING");
        distributeStates(CoreAPI.INSTANCE.getModInstances(),CommonEntryPoint::onServerStopping);
    }

    @Override
    public void onServerStopped() {
        TILRef.logInfo("TIL SERVER STOPPED");
        distributeStates(CoreAPI.INSTANCE.getModInstances(),CommonEntryPoint::onServerStopped);
    }
}