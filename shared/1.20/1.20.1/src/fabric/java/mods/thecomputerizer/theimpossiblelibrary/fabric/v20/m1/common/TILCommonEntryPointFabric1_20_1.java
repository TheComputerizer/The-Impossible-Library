package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.common;

import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.TILCommonEntryPointFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m1.server.WrappedCommand1_20_1;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.tab.CreativeTabBuilder1_20;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.shared.v20.m1.server.WrappedCommand1_20_1.INFO;
import static net.minecraft.core.registries.BuiltInRegistries.COMMAND_ARGUMENT_TYPE;

public class TILCommonEntryPointFabric1_20_1 extends TILCommonEntryPointFabric1_20 {
    
    private static TILCommonEntryPointFabric1_20_1 INSTANCE;
    
    public static TILCommonEntryPointFabric1_20_1 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointFabric1_20_1();
    }
    
    private TILCommonEntryPointFabric1_20_1() {
        INSTANCE = this;
    }
    
    @Override public void onCommonSetup() {
        WrappedCommand1_20_1.registerArgType();
        Registry.register(COMMAND_ARGUMENT_TYPE,new ResourceLocation(MODID,"custom_suggester"),INFO);
        super.onCommonSetup();
    }
    
    @Override public void onLoadComplete() {
        FabricHelper.registerServerHooks();
        CreativeTabBuilder1_20.onRegister(null);
        super.onLoadComplete();
    }
}
