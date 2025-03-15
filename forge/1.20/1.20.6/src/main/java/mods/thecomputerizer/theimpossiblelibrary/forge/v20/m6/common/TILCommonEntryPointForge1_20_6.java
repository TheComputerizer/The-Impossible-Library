package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.common;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.common.TILCommonEntryPoint1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.tab.CreativeTabBuilder1_20;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import javax.annotation.Nullable;
import java.util.Objects;

import static net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB;

public class TILCommonEntryPointForge1_20_6 extends TILCommonEntryPoint1_20_6 {
    
    private static TILCommonEntryPointForge1_20_6 INSTANCE;
    
    public static TILCommonEntryPointForge1_20_6 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointForge1_20_6();
    }
    
    public static void onRegisterCreativeTabs(RegisterEvent event) {
        if(event.getRegistryKey()==CREATIVE_MODE_TAB) CreativeTabBuilder1_20.onRegister(event);
    }
    
    public static void onSupplyCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        CreativeTabBuilder1_20.onSupply(event);
    }
    
    private TILCommonEntryPointForge1_20_6() {
        INSTANCE = this;
    }
    
    @Nullable IEventBus getModBus() {
        if(Objects.isNull(this.extraData)) {
            TILRef.logWarn("(Forge 1.20.6) Extra data not found! Attempting to extract from context");
            this.extraData = ModLoadingContext.get().extension();
        }
        if(this.extraData instanceof FMLJavaModLoadingContext)
            return ((FMLJavaModLoadingContext)this.extraData).getModEventBus();
        TILRef.logError("(Forge 1.20.6) Extra data not set to instance of FMLJavaModLoadingContext! {}",this.extraData);
        return null;
    }
    
    @Override public void onPreRegistration() {
        IEventBus bus = getModBus();
        if(Objects.nonNull(bus)) bus.addListener(TILCommonEntryPointForge1_20_6::onRegisterCreativeTabs);
        super.onPreRegistration();
    }
    
    @Override public void onCommonSetup() {
        IEventBus bus = getModBus();
        if(Objects.nonNull(bus)) bus.addListener(TILCommonEntryPointForge1_20_6::onSupplyCreativeTabs);
        super.onCommonSetup();
    }
}