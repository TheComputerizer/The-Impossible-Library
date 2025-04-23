package mods.thecomputerizer.theimpossiblelibrary.forge.v21.m1.common;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.TILCommonEntryPoint1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.tab.CreativeTabBuilder1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.WrappedCommand1_21;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import javax.annotation.Nullable;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.WrappedCommand1_21.INFO;
import static net.minecraft.core.registries.Registries.COMMAND_ARGUMENT_TYPE;
import static net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB;

public class TILCommonEntryPointForge1_21_1 extends TILCommonEntryPoint1_21 {
    
    private static TILCommonEntryPointForge1_21_1 INSTANCE;
    
    public static TILCommonEntryPointForge1_21_1 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointForge1_21_1();
    }
    
    public static void onRegisterCreativeTabs(RegisterEvent event) {
        if(event.getRegistryKey()==CREATIVE_MODE_TAB) CreativeTabBuilder1_21.onRegister(event);
        else if(event.getRegistryKey()==COMMAND_ARGUMENT_TYPE) {
            ResourceLocation registryName = ResourceLocation.fromNamespaceAndPath(MODID,"custom_suggester");
            event.register(COMMAND_ARGUMENT_TYPE,registryName,() -> INFO);
            WrappedCommand1_21.registerArgType();
        }
    }
    
    public static void onSupplyCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        CreativeTabBuilder1_21.onSupply(event);
    }
    
    private TILCommonEntryPointForge1_21_1() {
        INSTANCE = this;
    }
    
    @Nullable IEventBus getModBus() {
        if(Objects.isNull(this.extraData)) {
            TILRef.logWarn("(Forge 1.21.1) Extra data not found! Attempting to extract from context");
            this.extraData = ModLoadingContext.get().extension();
        }
        if(this.extraData instanceof FMLJavaModLoadingContext)
            return ((FMLJavaModLoadingContext)this.extraData).getModEventBus();
        TILRef.logError("(Forge 1.21.1) Extra data not set to instance of FMLJavaModLoadingContext! {}",this.extraData);
        return null;
    }
    
    @Override public void onPreRegistration() {
        IEventBus bus = getModBus();
        if(Objects.nonNull(bus)) bus.addListener(TILCommonEntryPointForge1_21_1::onRegisterCreativeTabs);
        super.onPreRegistration();
    }
    
    @Override public void onCommonSetup() {
        IEventBus bus = getModBus();
        if(Objects.nonNull(bus)) bus.addListener(TILCommonEntryPointForge1_21_1::onSupplyCreativeTabs);
        super.onCommonSetup();
    }
}