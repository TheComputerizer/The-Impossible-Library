package mods.thecomputerizer.theimpossiblelibrary.api.common;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Common entrypoint API
 */
public abstract class CommonEntryPoint {

    protected CommonEntryPoint() {}

    protected void distributeStates(Collection<CommonEntryPoint> entryPoints, Consumer<CommonEntryPoint> func) {
        for(CommonEntryPoint entryPoint : entryPoints) func.accept(entryPoint);
    }

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