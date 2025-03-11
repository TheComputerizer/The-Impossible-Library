package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

/**
 * Client entrypoint API
 */
public abstract class ClientEntryPoint extends CommonEntryPoint {
    
    protected ClientEntryPoint() {
        super();
    }
    
    @IndirectCallers
    protected ClientEntryPoint(boolean root) {
        super(root);
    }

    public abstract void onClientSetup();
}