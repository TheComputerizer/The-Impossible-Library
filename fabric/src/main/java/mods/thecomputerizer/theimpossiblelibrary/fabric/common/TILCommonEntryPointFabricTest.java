package mods.thecomputerizer.theimpossiblelibrary.fabric.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.TILCommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import net.fabricmc.api.ModInitializer;

import static net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.CLIENT_STARTED;

/**
 * For testing the modloader before I start doing any stupid ASM stuff
 */
public class TILCommonEntryPointFabricTest implements ModInitializer {
    
    private final CommonEntryPoint entryPoint;
    
    public TILCommonEntryPointFabricTest() {
        this.entryPoint = TILCommonEntryPoint.getInstance();
        this.entryPoint.onConstructed();
        this.entryPoint.onPreRegistration();
    }
    
    @Override public void onInitialize() {
        this.entryPoint.onCommonSetup();
        FabricHelper.finalizeEntrypoints(this.entryPoint);
    }
}
