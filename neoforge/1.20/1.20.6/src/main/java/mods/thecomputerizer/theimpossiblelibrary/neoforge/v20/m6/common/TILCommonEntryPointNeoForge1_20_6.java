package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.common;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.network.NetworkNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.common.TILCommonEntryPoint1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.tab.CreativeTabBuilder1_20;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB;

public class TILCommonEntryPointNeoForge1_20_6 extends TILCommonEntryPoint1_20_6 {
    
    private static TILCommonEntryPointNeoForge1_20_6 INSTANCE;
    
    public static TILCommonEntryPointNeoForge1_20_6 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointNeoForge1_20_6();
    }
    
    public static void onRegisterCreativeTabs(RegisterEvent event) {
        if(event.getRegistryKey()==CREATIVE_MODE_TAB) CreativeTabBuilder1_20.onRegister(event);
    }
    
    public static void onSupplyCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        CreativeTabBuilder1_20.onSupply(event);
    }
    
    private TILCommonEntryPointNeoForge1_20_6() {
        INSTANCE = this;
    }
    
    @Nullable IEventBus getModBus() {
        if(Objects.isNull(this.extraData)) {
            TILRef.logWarn("(NeoForge 1.20.6) Extra data not found! Attempting to extract from context");
            ModContainer container = ModLoadingContext.get().getActiveContainer();
            if(Objects.nonNull(container)) this.extraData = container.getEventBus();
            else TILRef.logError("Failed to set extra data! The mod container doesnt exist?");
        }
        if(this.extraData instanceof IEventBus bus) return bus;
        TILRef.logError("Extra data not set to instance of IEventBus {}",this.extraData);
        return null;
    }
    
    @Override public void onPreRegistration() {
        IEventBus bus = getModBus();
        if(Objects.nonNull(bus)) bus.addListener(TILCommonEntryPointNeoForge1_20_6::onRegisterCreativeTabs);
        super.onPreRegistration();
    }
    
    @Override public void onCommonSetup() {
        IEventBus bus = getModBus();
        if(Objects.nonNull(bus)) {
            bus.addListener(TILCommonEntryPointNeoForge1_20_6::onSupplyCreativeTabs);
            bus.addListener(NetworkNeoForge1_20_6::registerPayloadServer);
        } else TILRef.logError("Failed to register network payloads!");
        CreativeTabBuilder1_20.onRegister(null);
        super.onCommonSetup();
    }
}