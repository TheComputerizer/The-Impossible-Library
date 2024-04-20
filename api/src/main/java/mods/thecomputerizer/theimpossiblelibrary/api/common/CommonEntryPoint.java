package mods.thecomputerizer.theimpossiblelibrary.api.common;

/**
 * Common entrypoint API
 */
public abstract class CommonEntryPoint {

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