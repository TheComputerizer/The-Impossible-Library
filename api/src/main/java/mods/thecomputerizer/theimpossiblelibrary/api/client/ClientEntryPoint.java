package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;

/**
 * Client entrypoint API
 */
public abstract class ClientEntryPoint extends CommonEntryPoint {
    
    protected ClientEntryPoint() {
        super();
    }
    
    protected ClientEntryPoint(boolean root) {
        super(root);
    }

    public abstract void onClientSetup();
}