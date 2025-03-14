package mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.common;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.TILCommonEntryPoint1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.registry.tab.CreativeTabBuilder1_19_4;
import net.minecraftforge.event.CreativeModeTabEvent.BuildContents;
import net.minecraftforge.event.CreativeModeTabEvent.Register;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Objects;

public class TILCommonEntryPointForge1_19_4 extends TILCommonEntryPoint1_19 {
    
    private static TILCommonEntryPointForge1_19_4 INSTANCE;
    
    public static TILCommonEntryPointForge1_19_4 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointForge1_19_4();
    }
    
    public static void onBuildCreativeTabs(Register event) {
        CreativeTabBuilder1_19_4.onRegister(event);
    }
    
    public static void onSupplyCreativeTabs(BuildContents event) {
        CreativeTabBuilder1_19_4.onSupply(event);
    }
    
    private TILCommonEntryPointForge1_19_4() {
        INSTANCE = this;
    }
    
    @Override public void onCommonSetup() {
        if(Objects.isNull(this.extraData)) {
            TILRef.logWarn("(Forge 1.19.4) Extra data not found! Attempting to extract from context");
            this.extraData = ModLoadingContext.get().extension();
        }
        if(this.extraData instanceof FMLJavaModLoadingContext) {
            IEventBus bus = ((FMLJavaModLoadingContext)this.extraData).getModEventBus();
            bus.addListener(TILCommonEntryPointForge1_19_4::onBuildCreativeTabs);
            bus.addListener(TILCommonEntryPointForge1_19_4::onSupplyCreativeTabs);
        } else TILRef.logError("(Forge 1.19.4) Extra data not set to instance of FMLJavaModLoadingContext! {}",
                               this.extraData);
        super.onCommonSetup();
    }
}