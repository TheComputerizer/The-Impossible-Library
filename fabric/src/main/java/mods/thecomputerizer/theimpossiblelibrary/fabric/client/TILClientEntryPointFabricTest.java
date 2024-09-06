package mods.thecomputerizer.theimpossiblelibrary.fabric.client;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.TILCommonEntryPoint;
import net.fabricmc.api.ClientModInitializer;

/**
 * For testing the modloader before I start doing any stupid ASM stuff
 */
public class TILClientEntryPointFabricTest implements ClientModInitializer {
    
    @Override public void onInitializeClient() {
        CommonEntryPoint entryPoint = TILCommonEntryPoint.getInstance();
        entryPoint.checkClientSetup();
        entryPoint.onInterModEnqueue();
    }
}