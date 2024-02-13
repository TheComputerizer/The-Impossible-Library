package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.ReferenceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;

/**
 * Client entrypoint API
 */
public abstract class ClientEntryPoint<TYPES extends CommonAPI> extends CommonEntryPoint<TYPES> {

    protected ClientEntryPoint(TYPES types) {
        super(types);
        if(ReferenceAPI.CLIENT) INSTANCE = this;
    }

    @Override
    protected void registerTypeShortcuts() {
        super.registerTypeShortcuts();
    }
}