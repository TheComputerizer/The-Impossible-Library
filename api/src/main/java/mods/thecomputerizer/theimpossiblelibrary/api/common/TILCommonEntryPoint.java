package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.client.TILClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionMod;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHandler;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;

import javax.annotation.Nullable;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.DESCRIPTION;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;

/**
 * For internal use only
 */
@MultiVersionMod(modDescription = DESCRIPTION, modid = MODID, modName = NAME, modVersion = VERSION)
public final class TILCommonEntryPoint extends CommonEntryPoint {

    private static TILCommonEntryPoint INSTANCE;
    
    private static void devTrace(String msg, Object ... args) {
        TILDev.logDebug("[TILCommonEntryPoint Trace]: "+msg,args);
    }

    public static TILCommonEntryPoint getInstance() {
        if(Objects.isNull(INSTANCE)) INSTANCE = new TILCommonEntryPoint();
        return INSTANCE;
    }

    private final CommonEntryPoint versionHandler;

    public TILCommonEntryPoint() {
        devTrace("constructor");
        TagHelper.initGlobal();
        this.versionHandler = CoreAPI.INSTANCE.getCommonVersionHandler();
    }

    @Override
    public @Nullable ClientEntryPoint delegatedClientEntry() {
        return TILClientEntryPoint.getInstance();
    }

    @Override
    protected String getModID() {
        return MODID;
    }

    @Override
    protected String getModName() {
        return NAME;
    }

    @Override
    public void onConstructed() {
        devTrace("onConstructed");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onConstructed();
    }

    @Override
    public void onPreRegistration() {
        devTrace("onPreRegistration");
        EventHelper.initTILListeners(false,TILDev.DEV);
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onPreRegistration();
        if(Objects.nonNull(this.delegatedClient)) this.delegatedClient.onPreRegistration();
    }

    @Override
    public void onCommonSetup() {
        devTrace("onCommonSetup");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onCommonSetup();
    }

    @Override
    public void onDedicatedServerSetup() {
        devTrace("onDedicatedServerSetup");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onDedicatedServerSetup();
    }

    @Override
    public void onInterModEnqueue() {
        devTrace("onInterModEnqueue");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onInterModEnqueue();
    }

    @Override
    public void onInterModProcess() {
        devTrace("onInterModProcess");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onInterModProcess();
    }

    @Override
    public void onLoadComplete() {
        devTrace("onLoadComplete");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onLoadComplete();
        NetworkHandler.load();
    }

    @Override
    public void onServerAboutToStart() {
        devTrace("onServerAboutToStart");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onServerAboutToStart();
    }

    @Override
    public void onServerStarting() {
        devTrace("onServerStarting");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onServerStarting();
    }

    @Override
    public void onServerStarted() {
        devTrace("onServerStarted");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onServerStarted();
    }

    @Override
    public void onServerStopping() {
        devTrace("onServerStopping");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onServerStopping();
    }

    @Override
    public void onServerStopped() {
        devTrace("onServerStopped");
        if(Objects.nonNull(this.versionHandler)) this.versionHandler.onServerStopped();
    }
}