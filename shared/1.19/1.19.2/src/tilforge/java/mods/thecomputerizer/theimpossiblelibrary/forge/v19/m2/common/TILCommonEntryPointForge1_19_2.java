package mods.thecomputerizer.theimpossiblelibrary.forge.v19.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.TILCommonEntryPoint1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.server.WrappedCommand1_19;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.shared.v19.server.WrappedCommand1_19.INFO;
import static net.minecraft.core.Registry.COMMAND_ARGUMENT_TYPE_REGISTRY;

public class TILCommonEntryPointForge1_19_2 extends TILCommonEntryPoint1_19 {
    
    private static TILCommonEntryPointForge1_19_2 INSTANCE;
    
    public static TILCommonEntryPointForge1_19_2 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointForge1_19_2();
    }
    
    public static void onRegisterEvent(RegisterEvent event) {
        if(event.getRegistryKey()==COMMAND_ARGUMENT_TYPE_REGISTRY) {
            ResourceLocation registryName = ResourceLocation.fromNamespaceAndPath(MODID,"custom_suggester");
            event.register(COMMAND_ARGUMENT_TYPE_REGISTRY,registryName,() -> INFO);
            WrappedCommand1_19.registerArgType();
        }
    }
    
    private TILCommonEntryPointForge1_19_2() {
        INSTANCE = this;
    }
    
    @Override public void onCommonSetup() {
        if(Objects.isNull(this.extraData)) {
            TILRef.logWarn("(Forge 1.19.2) Extra data not found! Attempting to extract from context");
            this.extraData = ModLoadingContext.get().extension();
        }
        if(this.extraData instanceof FMLJavaModLoadingContext) {
            IEventBus bus = ((FMLJavaModLoadingContext)this.extraData).getModEventBus();
            bus.addListener(TILCommonEntryPointForge1_19_2::onRegisterEvent);
        } else TILRef.logError("(Forge 1.19.2) Extra data not set to instance of FMLJavaModLoadingContext! {}",
                               this.extraData);
        super.onCommonSetup();
    }
}