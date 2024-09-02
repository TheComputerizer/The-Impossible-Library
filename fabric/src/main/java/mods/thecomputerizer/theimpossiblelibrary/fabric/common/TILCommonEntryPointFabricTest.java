package mods.thecomputerizer.theimpossiblelibrary.fabric.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.TILCommonEntryPoint;
import net.fabricmc.api.ModInitializer;

/**
 * For testing the modloader before I start doing any stupid ASM stuff
 */
public class TILCommonEntryPointFabricTest implements ModInitializer {
    
    private final CommonEntryPoint entryPoint;
    
    public TILCommonEntryPointFabricTest() {
        this.entryPoint = TILCommonEntryPoint.getInstance();
        this.entryPoint.onConstructed();
    }
    
    /**
     * Can both of these be in the same method??
     */
    @Override public void onInitialize() {
        this.entryPoint.onPreRegistration();
        this.entryPoint.onCommonSetup();
    }
}
