package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.client.TILClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.MultiVersionMod;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHandler;
import mods.thecomputerizer.theimpossiblelibrary.api.server.TILServerEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.DESCRIPTION;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;

/**
 * For internal use only
 */
@MultiVersionMod(modDescription = DESCRIPTION, modid = MODID, modName = NAME, modVersion = VERSION)
public final class TILCommonEntryPoint extends DelegatingCommonEntryPoint {

    private static TILCommonEntryPoint INSTANCE;
    
    private static void devTrace(String msg, Object ... args) {
        TILRef.logInfo("[TILCommonEntryPoint Trace]: "+msg, args);
    }

    public static TILCommonEntryPoint getInstance() {
        if(Objects.isNull(INSTANCE)) INSTANCE = new TILCommonEntryPoint();
        return INSTANCE;
    }

    public TILCommonEntryPoint() {
        devTrace("constructor");
        TagHelper.initGlobal();
    }

    @Override protected String getModID() {
        return MODID;
    }

    @Override protected String getModName() {
        return NAME;
    }

    @Override public void onConstructed() {
        devTrace("onConstructed");
        super.onConstructed();
    }

    @Override public void onPreRegistration() {
        devTrace("onPreRegistration");
        EventHelper.initTILListeners(false,true,false,DEV);
        super.onPreRegistration();
    }

    @Override public void onCommonSetup() {
        devTrace("onCommonSetup");
        super.onCommonSetup();
    }

    @Override public void onDedicatedServerSetup() {
        devTrace("onDedicatedServerSetup");
    }

    @Override public void onInterModEnqueue() {
        devTrace("onInterModEnqueue");
        super.onInterModEnqueue();
    }

    @Override public void onInterModProcess() {
        devTrace("onInterModProcess");
        super.onInterModProcess();
    }

    @Override public void onLoadComplete() {
        devTrace("onLoadComplete");
        super.onLoadComplete();
        NetworkHandler.load();
    }

    @Override public void onServerAboutToStart() {
        devTrace("onServerAboutToStart");
        super.onServerAboutToStart();
    }

    @Override public void onServerStarting() {
        devTrace("onServerStarting");
        super.onServerStarting();
    }

    @Override public void onServerStarted() {
        devTrace("onServerStarted");
        super.onServerStarted();
    }

    @Override public void onServerStopping() {
        devTrace("onServerStopping");
        super.onServerStopping();
    }

    @Override public void onServerStopped() {
        devTrace("onServerStopped");
        super.onServerStopped();
    }
    
    @Override public ClientEntryPoint setDelegatedClientHandle() {
        devTrace("setDelegatedClientHandle");
        return TILClientEntryPoint.getInstance();
    }
    
    @Override public CommonEntryPoint setDelegatedCustomHandle() {
        devTrace("setDelegatedCustomHandle");
        return CoreAPI.getInstance().getCommonVersionHandler();
    }
    
    @Override public CommonEntryPoint setDelegatedServerHandle() {
        devTrace("setDelegatedServerHandle");
        return TILServerEntryPoint.getInstance();
    }
}