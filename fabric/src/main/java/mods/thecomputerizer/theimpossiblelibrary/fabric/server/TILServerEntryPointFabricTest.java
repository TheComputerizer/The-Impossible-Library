package mods.thecomputerizer.theimpossiblelibrary.fabric.server;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.TILCommonEntryPoint;
import net.fabricmc.api.DedicatedServerModInitializer;

/**
 * For testing the modloader before I start doing any stupid ASM stuff
 */
public class TILServerEntryPointFabricTest implements DedicatedServerModInitializer {
    
    @Override public void onInitializeServer() {
        CommonEntryPoint entryPoint = TILCommonEntryPoint.getInstance();
        entryPoint.checkDedicatedServerSetup();
        entryPoint.onInterModEnqueue();
    }
}
