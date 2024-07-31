package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;

import javax.annotation.Nullable;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.INSTANCE;

/**
 * Common entrypoint API
 */
public abstract class CommonEntryPoint {

    protected final ClientEntryPoint delegatedClient;

    protected CommonEntryPoint() {
        INSTANCE.modConstructed(getClass().getPackage(),getModID(),getModName(),
                this instanceof ClientEntryPoint ? "Client" : "Common");
        this.delegatedClient = INSTANCE.getSide().isClient() ? delegatedClientEntry() : null;
    }

    public final void checkClientSetup() {
        if(Objects.nonNull(this.delegatedClient)) this.delegatedClient.onClientSetup();
    }

    public final void checkDedicatedServerSetup() {
        if(INSTANCE.getSide().isServer()) onDedicatedServerSetup();
    }

    public abstract @Nullable ClientEntryPoint delegatedClientEntry();
    protected abstract String getModID();
    protected abstract String getModName();
    public void onConstructed() {}
    public void onPreRegistration() {}
    public void onCommonSetup() {}
    protected void onDedicatedServerSetup() {}
    public void onInterModEnqueue() {}
    public void onInterModProcess() {}
    public void onLoadComplete() {}
    public void onServerAboutToStart() {}
    public void onServerStarting() {}
    public void onServerStarted() {}
    public void onServerStopping() {}
    public void onServerStopped() {}
}