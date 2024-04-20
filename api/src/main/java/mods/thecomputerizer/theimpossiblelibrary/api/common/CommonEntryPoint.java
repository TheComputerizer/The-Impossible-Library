package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;

/**
 * Common entrypoint API
 */
public abstract class CommonEntryPoint {

    /**
     * Any class that extends this
     */
    protected CommonEntryPoint() {
        CoreAPI.INSTANCE.modConstructed(getClass().getPackage(),getModID(),getModName(),"Common");
    }

    protected abstract String getModID();
    protected abstract String getModName();
    public void onConstructed() {}
    public void onPreRegistration() {}
    public void onCommonSetup() {}
    public void onDedicatedServerSetup() {}
    public void onInterModEnqueue() {}
    public void onInterModProcess() {}
    public void onLoadComplete() {}
    public void onServerAboutToStart() {}
    public void onServerStarting() {}
    public void onServerStarted() {}
    public void onServerStopping() {}
    public void onServerStopped() {}
}