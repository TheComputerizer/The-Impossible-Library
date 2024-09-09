package mods.thecomputerizer.theimpossiblelibrary.fabric.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.common.TILCommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;

/**
 * For testing the modloader before I start doing any stupid ASM stuff
 */
public class TILCommonEntryPointFabricTest implements ModInitializer {
    
    public static TILCommonEntryPointFabricTest INSTANCE;
    
    private final CommonEntryPoint entryPoint;
    
    public TILCommonEntryPointFabricTest() {
        INSTANCE = this;
        this.entryPoint = TILCommonEntryPoint.getInstance();
        this.entryPoint.onConstructed();
        this.entryPoint.onPreRegistration();
    }
    
    @Override public void onInitialize() {
        this.entryPoint.onCommonSetup();
        FabricHelper.finalizeEntrypoints(this.entryPoint);
    }
    
    @IndirectCallers
    public static class LoaderClient implements ClientModInitializer {
        
        @Override public void onInitializeClient() {
            INSTANCE.entryPoint.checkClientSetup();
            INSTANCE.entryPoint.onInterModEnqueue();
        }
    }
    
    @IndirectCallers
    public static class LoaderServer implements DedicatedServerModInitializer {
        
        @Override public void onInitializeServer() {
            INSTANCE.entryPoint.checkDedicatedServerSetup();
            INSTANCE.entryPoint.onInterModEnqueue();
        }
    }
}