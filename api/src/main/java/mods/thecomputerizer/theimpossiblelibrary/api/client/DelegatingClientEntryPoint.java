package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;

/**
 * ClientEntryPoint implementation with delegation already set up for every entrypoint method.
 * The onClientSetup method is specially handled within CommonEntryPoint.
 * Don't forget to call the super methods if you want the delegation to actually work.
 */
public abstract class DelegatingClientEntryPoint extends ClientEntryPoint {
    
    public void onConstructed() {
        handleAll(CommonEntryPoint::onConstructed);
    }
    
    public void onPreRegistration() {
        handleAll(CommonEntryPoint::onPreRegistration);
    }
    
    public void onCommonSetup() {
        handleAll(CommonEntryPoint::onCommonSetup);
    }
    
    public void onInterModEnqueue() {
        handleAll(CommonEntryPoint::onInterModEnqueue);
    }
    
    public void onInterModProcess() {
        handleAll(CommonEntryPoint::onInterModProcess);
    }
    
    public void onLoadComplete() {
        handleAll(CommonEntryPoint::onLoadComplete);
    }
    
    public void onServerAboutToStart() {
        handleAll(CommonEntryPoint::onServerAboutToStart);
    }
    
    public void onServerStarting() {
        handleAll(CommonEntryPoint::onServerStarting);
    }
    
    public void onServerStarted() {
        handleAll(CommonEntryPoint::onServerStarted);
    }
    
    public void onServerStopping() {
        handleAll(CommonEntryPoint::onServerStopping);
    }
    
    public void onServerStopped() {
        handleAll(CommonEntryPoint::onServerStopped);
    }
}
