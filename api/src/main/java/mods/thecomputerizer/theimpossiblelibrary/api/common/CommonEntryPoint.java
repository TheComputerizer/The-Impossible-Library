package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Common entrypoint API
 * Handle run order: this, client, server, custom
 * It is highly recommended to define delegate handlers statically to avoid any duplicates in the entrypoint chain.
 */
public abstract class CommonEntryPoint {
    
    protected final ClientEntryPoint delegatedClientHandle;
    protected final CommonEntryPoint delegatedCustomHandle;
    protected final CommonEntryPoint delegatedServerHandle;
    protected CommonEntryPoint parent;
    protected Object extraData; //In the case of a parameterized constructor. Could be Object[]
    
    protected CommonEntryPoint() {
        this(true);
    }

    protected CommonEntryPoint(boolean root) {
        if(root) CoreAPI.getInstance().modConstructed(getClass().getPackage(),getModID(),getModName(),
                this instanceof ClientEntryPoint ? "Client" : "Common");
        this.delegatedClientHandle = CoreAPI.isClient() ? checkThis(setDelegatedClientHandle()) : null;
        this.delegatedServerHandle = CoreAPI.isServer() ? checkThis(setDelegatedServerHandle()) : null;
        this.delegatedCustomHandle = checkThis(setDelegatedCustomHandle());
    }
    
    protected final void onHandleAdded(CommonEntryPoint handle) {
        handle.parent = this;
        handle.extraData = this.extraData;
    }
    
    /**
     * Avoid potential infinite loops via delegation
     */
    private <E extends CommonEntryPoint> E checkThis(E handle) {
        handle = verifyNoDuplicates(handle) ? handle : null;
        if(Objects.nonNull(handle)) onHandleAdded(handle);
        return handle;
    }

    public final void checkClientSetup() {
        if(CoreAPI.isClient()) checkClientSetupInner();
    }
    
    /**
     * Runs after CoreAPI#isClient is verified
     */
    protected final void checkClientSetupInner() {
        handleWithClientCustom(ClientEntryPoint::onClientSetup,false);
    }

    public final void checkDedicatedServerSetup() {
        if(CoreAPI.isServer()) checkDedicatedServerSetupInner();
    }
    
    /**
     * Runs after CoreAPI#isServer is verified
     */
    protected final void checkDedicatedServerSetupInner() {
        handleWithServerCustom(CommonEntryPoint::onDedicatedServerSetup,false);
    }
    
    protected abstract String getModID();
    protected abstract String getModName();
    
    /**
     * Handles a CommonEntryPoint method on this and all known handles other than this via the commonHandle input.
     * Each ClientEntryPoint handle will instead be handled via the clientHandle input.
     */
    @IndirectCallers
    protected final void handleAll(Consumer<CommonEntryPoint> commonHandle, Consumer<ClientEntryPoint> clientHandle) {
        handleAll(commonHandle,clientHandle,true);
    }
    
    /**
     * Handles a CommonEntryPoint method on this and all known handles via the commonHandle input.
     * Each ClientEntryPoint handle (including this object) will instead be handled via the clientHandle input.
     * If ignoreThis is enabled, all handles other than this will be run (the typical desired use case).
     */
    @SuppressWarnings("SameParameterValue")
    protected final void handleAll(Consumer<CommonEntryPoint> commonHandle, Consumer<ClientEntryPoint> clientHandle,
            boolean ignoreThis) {
        if(!ignoreThis) handleEither(this,commonHandle,clientHandle);
        handleEither(this.delegatedClientHandle,commonHandle,clientHandle);
        handleEither(this.delegatedCustomHandle,commonHandle,clientHandle);
        handleEither(this.delegatedServerHandle,commonHandle,clientHandle);
    }
    
    /**
     * Handles a CommonEntryPoint method on this and all known handles other than this.
     */
    @IndirectCallers
    protected final void handleAll(Consumer<CommonEntryPoint> commonHandle) {
        handleAll(commonHandle,true);
    }
    
    /**
     * Handles a CommonEntryPoint method on this and all known handles.
     * If ignoreThis is enabled, all handles other than this will be run (the typical desired use case).
     */
    @SuppressWarnings("SameParameterValue")
    protected final void handleAll(Consumer<CommonEntryPoint> commonHandle, boolean ignoreThis) {
        if(!ignoreThis) commonHandle.accept(this);
        if(Objects.nonNull(this.delegatedClientHandle)) commonHandle.accept(this.delegatedClientHandle);
        if(Objects.nonNull(this.delegatedCustomHandle)) commonHandle.accept(this.delegatedCustomHandle);
        if(Objects.nonNull(this.delegatedServerHandle)) commonHandle.accept(this.delegatedServerHandle);
    }
    
    /**
     * Handles either a CommonEntryPoint or ClientEntryPoint method on the input delegate, depending on whether it is
     * an instance of ClientEntryPoint.
     */
    protected final void handleEither(@Nullable CommonEntryPoint delegate, Consumer<CommonEntryPoint> commonHandle,
            Consumer<ClientEntryPoint> clientHandle) {
        if(Objects.nonNull(delegate)) {
            if(this instanceof ClientEntryPoint) clientHandle.accept((ClientEntryPoint)delegate);
            else commonHandle.accept(delegate);
        }
    }
    
    /**
     * Handles a ClientEntryPoint method on the delegatedClientHandle if it exists.
     */
    @IndirectCallers
    protected final void handleWithClient(Consumer<ClientEntryPoint> clientHandle) {
        handleWithClient(clientHandle,true);
    }
    
    /**
     * Handles a ClientEntryPoint method on only this object if is an isntance of ClientEntryPoint and the
     * delegatedClientHandle if it exists.
     * If strict is enabled, only the delegatedClientHandle is run.
     */
    @SuppressWarnings("SameParameterValue")
    protected final void handleWithClient(Consumer<ClientEntryPoint> clientHandle, boolean strict) {
        if(!strict && this instanceof ClientEntryPoint) clientHandle.accept((ClientEntryPoint)this);
        if(Objects.nonNull(this.delegatedClientHandle)) clientHandle.accept(this.delegatedClientHandle);
    }
    
    /**
     * Handles a ClientEntryPoint method on the delegatedClientHandle if it exists
     * and the delegatedCustomHandle if it exists and is an instance of ClientEntryPoint.
     */
    @IndirectCallers
    protected final void handleWithClientCustom(Consumer<ClientEntryPoint> clientHandle) {
        handleWithClientCustom(clientHandle,true);
    }
    
    /**
     * Handles a ClientEntryPoint method on only this object, the delegatedClientHandle if it exists, and the
     * delegatedCustomHandle if it exists and is an instance of ClientEntryPoint.
     * If strict is enabled, only the delegatedClientHandle and delegatedCustomHandle are run.
     */
    @SuppressWarnings("SameParameterValue")
    protected final void handleWithClientCustom(Consumer<ClientEntryPoint> clientHandle, boolean strict) {
        handleWithClient(clientHandle,strict);
        if(this.delegatedCustomHandle instanceof ClientEntryPoint)
            clientHandle.accept((ClientEntryPoint)this.delegatedCustomHandle);
    }
    
    /**
     * Handles a CommonEntryPoint method on only this object and the delegatedCustomHandle if it exists.
     */
    @IndirectCallers
    protected final void handleWithCustom(Consumer<CommonEntryPoint> customHandle) {
        handleWithCustom(customHandle,false);
    }
    
    /**
     * Handles a CommonEntryPoint method on only this object and the delegatedCustomHandle if it exists.
     * If strict is enabled, only the delegatedCustomHandle is run.
     */
    @SuppressWarnings("SameParameterValue")
    protected final void handleWithCustom(Consumer<CommonEntryPoint> customHandle, boolean strict) {
        if(!strict) customHandle.accept(this);
        if(Objects.nonNull(this.delegatedCustomHandle)) customHandle.accept(this.delegatedCustomHandle);
    }
    
    /**
     * Handles a CommonEntryPoint method on the delegatedServerHandle if it exists.
     */
    @IndirectCallers
    protected final void handleWithServer(Consumer<CommonEntryPoint> serverHandle) {
        handleWithServer(serverHandle,true);
    }
    
    /**
     * Handles a CommonEntryPoint method on only this object and the delegatedServerHandle if it exists.
     * If strict is enabled, only the delegatedServerHandle is run
     */
    @SuppressWarnings("SameParameterValue")
    protected final void handleWithServer(Consumer<CommonEntryPoint> serverHandle, boolean strict) {
        if(!strict) serverHandle.accept(this);
        if(Objects.nonNull(this.delegatedServerHandle)) serverHandle.accept(this.delegatedServerHandle);
    }
    
    /**
     * Handles a CommonEntryPoint method on the delegatedServerHandle if it exists
     * and the delegatedCustomHandle if it exists.
     */
    @IndirectCallers
    protected final void handleWithServerCustom(Consumer<CommonEntryPoint> serverHandle) {
        handleWithServerCustom(serverHandle,true);
    }
    
    /**
     * Handles a CommonEntryPoint method on only this object, the delegatedServerHandle if it exists, and the
     * delegatedCustomHandle if it exists.
     * If strict is enabled, only the delegatedServerHandle and delegatedCustomHandle are run.
     */
    @SuppressWarnings("SameParameterValue")
    protected final void handleWithServerCustom(Consumer<CommonEntryPoint> serverHandle, boolean strict) {
        handleWithServer(serverHandle,strict);
        if(Objects.nonNull(this.delegatedCustomHandle)) serverHandle.accept(this.delegatedCustomHandle);
    }
    
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
    
    /**
     * Defines a delegate for handling client stuff specific to ClientEntryPoint
     */
    public @Nullable ClientEntryPoint setDelegatedClientHandle() {
        return null;
    }
    
    /**
     * Defines a delegate for handling a custom extension of CommonEntryPoint.
     * Used internally to handle some version-specific stuff
     */
    public @Nullable CommonEntryPoint setDelegatedCustomHandle() {
        return null;
    }
    
    /**
     * Defines a delegate for handling CommonEntryPoint stuff that only runs on servers
     */
    public @Nullable CommonEntryPoint setDelegatedServerHandle() {
        return null;
    }
    
    /**
     * Called via ASM or a parent delegator
     */
    @IndirectCallers
    public void setExtraData(Object data) {
        this.extraData = data;
        if(Objects.nonNull(this.delegatedClientHandle)) this.delegatedClientHandle.setExtraData(data);
        if(Objects.nonNull(this.delegatedCustomHandle)) this.delegatedCustomHandle.setExtraData(data);
        if(Objects.nonNull(this.delegatedServerHandle)) this.delegatedServerHandle.setExtraData(data);
    }
    
    /**
     * Extra guardrail against the same handler trying to get defined more than once in an entrypoint chain.
     * This won't guard against the same handler type being instantiated multiple times, so be careful.
     */
    protected boolean verifyNoDuplicates(@Nullable CommonEntryPoint handler) {
        if(Objects.isNull(handler)) return true;
        return Objects.nonNull(this.parent) ? this.parent.verifyNoDuplicates(handler) : verifyNoDuplicatesInner(handler);
    }
    
    /**
     * Assumes all the parents are null or have all been checked and that the handler is not null
     */
    protected boolean verifyNoDuplicatesInner(CommonEntryPoint handler) {
        if(handler==this) return false;
        if(Objects.nonNull(this.delegatedClientHandle) && !this.delegatedClientHandle.verifyNoDuplicatesInner(handler))
            return false;
        if(Objects.nonNull(this.delegatedCustomHandle) && !this.delegatedCustomHandle.verifyNoDuplicatesInner(handler))
            return false;
        return Objects.isNull(this.delegatedServerHandle) || this.delegatedServerHandle.verifyNoDuplicatesInner(handler);
    }
}