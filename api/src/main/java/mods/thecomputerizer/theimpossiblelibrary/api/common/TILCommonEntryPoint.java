package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.client.TILClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionMod;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHandler;

import javax.annotation.Nullable;
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
        TILDev.logInfo("TIL COMMON CONSTRUCTOR");
        this.versionHandler = CoreAPI.INSTANCE.getCommonVersionHandler();
    }

    @Override
    public @Nullable ClientEntryPoint delegatedClientEntry() {
        return TILClientEntryPoint.getInstance();
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
    public void onConstructed() {
        TILDev.logInfo("TIL COMMON CONSTRUCTED");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onConstructed();
    }

    @Override
    public void onPreRegistration() {
        TILDev.logInfo("TIL COMMON PRE REGISTRATION");
        NetworkHandler.load();
        EventHelper.initTILListeners(false,TILDev.DEV);
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onPreRegistration();
        if(Objects.nonNull(this.delegatedClient)) this.delegatedClient.onPreRegistration();
    }

    @Override
    public void onCommonSetup() {
        TILDev.logInfo("TIL COMMON SETUP");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onCommonSetup();
    }

    @Override
    public void onDedicatedServerSetup() {
        TILDev.logInfo("TIL DEDICATED SERVER SETUP");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onDedicatedServerSetup();
    }

    @Override
    public void onInterModEnqueue() {
        TILDev.logInfo("TIL INTER MOD ENQUEUE");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onInterModEnqueue();
    }

    @Override
    public void onInterModProcess() {
        TILDev.logInfo("TIL INTER MOD PROCESS");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onInterModProcess();
    }

    @Override
    public void onLoadComplete() {
        TILDev.logInfo("TIL LOAD COMPLETE");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onLoadComplete();
    }

    @Override
    public void onServerAboutToStart() {
        TILDev.logInfo("TIL SERVER ABOUT TO START");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onServerAboutToStart();
    }

    @Override
    public void onServerStarting() {
        TILDev.logInfo("TIL SERVER STARTING");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onServerStarting();
    }

    @Override
    public void onServerStarted() {
        TILDev.logInfo("TIL SERVER STARTED");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onServerStarted();
    }

    @Override
    public void onServerStopping() {
        TILDev.logInfo("TIL SERVER STOPPING");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onServerStopping();
    }

    @Override
    public void onServerStopped() {
        TILDev.logInfo("TIL SERVER STOPPED");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onServerStopped();
    }
}