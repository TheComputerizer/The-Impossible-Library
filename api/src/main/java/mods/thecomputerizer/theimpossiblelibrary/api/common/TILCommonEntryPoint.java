package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.TILClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionMod;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHandler;

import java.util.Objects;

/**
 * For internal use only
 */
@MultiVersionMod(modid = TILRef.MODID, modName = TILRef.NAME, modVersion = TILRef.VERSION)
public final class TILCommonEntryPoint extends CommonEntryPoint {

    private static TILCommonEntryPoint INSTANCE;

    public static TILCommonEntryPoint getInstance() {
        if(Objects.isNull(INSTANCE)) INSTANCE = new TILCommonEntryPoint();
        return INSTANCE;
    }

    private final CommonEntryPoint versionHandler;

    public TILCommonEntryPoint() {
        TILRef.logInfo("TIL COMMON CONSTRUCTOR");
        this.versionHandler = CoreAPI.INSTANCE.getCommonVersionHandler();
        if(CoreAPI.INSTANCE.getSide().isClient()) TILClientEntryPoint.getInstance();
    }

    @Override
    public void onConstructed() {
        TILRef.logInfo("TIL COMMON CONSTRUCTED");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onConstructed();
    }

    @Override
    public void onPreRegistration() {
        TILRef.logInfo("TIL COMMON PRE REGISTRATION");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onPreRegistration();
    }

    @Override
    public void onCommonSetup() {
        TILRef.logInfo("TIL COMMON SETUP");
        NetworkHandler.load();
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onCommonSetup();
    }

    @Override
    public void onDedicatedServerSetup() {
        TILRef.logInfo("TIL DEDICATED SERVER SETUP");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onDedicatedServerSetup();
    }

    @Override
    public void onInterModEnqueue() {
        TILRef.logInfo("TIL INTER MOD ENQUEUE");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onInterModEnqueue();
    }

    @Override
    public void onInterModProcess() {
        TILRef.logInfo("TIL INTER MOD PROCESS");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onInterModProcess();
    }

    @Override
    public void onLoadComplete() {
        TILRef.logInfo("TIL LOAD COMPLETE");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onLoadComplete();
    }

    @Override
    public void onServerAboutToStart() {
        TILRef.logInfo("TIL SERVER ABOUT TO START");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onServerAboutToStart();
    }

    @Override
    public void onServerStarting() {
        TILRef.logInfo("TIL SERVER STARTING");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onServerStarting();
    }

    @Override
    public void onServerStarted() {
        TILRef.logInfo("TIL SERVER STARTED");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onServerStarted();
    }

    @Override
    public void onServerStopping() {
        TILRef.logInfo("TIL SERVER STOPPING");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onServerStopping();
    }

    @Override
    public void onServerStopped() {
        TILRef.logInfo("TIL SERVER STOPPED");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onServerStopped();
    }
}