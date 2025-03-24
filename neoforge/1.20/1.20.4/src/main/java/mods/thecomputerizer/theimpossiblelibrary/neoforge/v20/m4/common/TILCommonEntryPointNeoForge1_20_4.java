package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.network.NetworkNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.common.TILCommonEntryPoint1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.server.WrappedCommand1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.tab.CreativeTabBuilder1_20;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.server.WrappedCommand1_20_4.INFO;
import static net.minecraft.core.registries.Registries.COMMAND_ARGUMENT_TYPE;
import static net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB;

public class TILCommonEntryPointNeoForge1_20_4 extends TILCommonEntryPoint1_20_4 {
    
    private static TILCommonEntryPointNeoForge1_20_4 INSTANCE;
    
    public static TILCommonEntryPointNeoForge1_20_4 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointNeoForge1_20_4();
    }
    
    public static void onRegisterCreativeTabs(RegisterEvent event) {
        if(event.getRegistryKey()==CREATIVE_MODE_TAB) CreativeTabBuilder1_20.onRegister(event);
        else if(event.getRegistryKey()==COMMAND_ARGUMENT_TYPE) {
            ResourceLocation registryName = new ResourceLocation(MODID,"custom_suggester");
            event.register(COMMAND_ARGUMENT_TYPE,registryName,() -> INFO);
            WrappedCommand1_20_4.registerArgType();
        }
    }
    
    public static void onSupplyCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        CreativeTabBuilder1_20.onSupply(event);
    }
    
    private TILCommonEntryPointNeoForge1_20_4() {
        INSTANCE = this;
    }
    
    @Nullable IEventBus getModBus() {
        if(Objects.isNull(this.extraData)) {
            TILRef.logWarn("(NeoForge 1.20.4) Extra data not found! Attempting to extract from context");
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
        if(Objects.nonNull(bus)) bus.addListener(TILCommonEntryPointNeoForge1_20_4::onRegisterCreativeTabs);
        super.onPreRegistration();
    }
    
    @Override public void onCommonSetup() {
        IEventBus bus = getModBus();
        if(Objects.nonNull(bus)) {
            bus.addListener(TILCommonEntryPointNeoForge1_20_4::onSupplyCreativeTabs);
            bus.addListener(NetworkNeoForge1_20_4::registerPayloads);
        } else TILRef.logError("Failed to register network payloads!");
        super.onCommonSetup();
    }
}