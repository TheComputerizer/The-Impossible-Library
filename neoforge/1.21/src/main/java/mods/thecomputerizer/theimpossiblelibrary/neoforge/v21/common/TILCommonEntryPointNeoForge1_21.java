package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.common;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.network.NetworkNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.TILCommonEntryPoint1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.tab.CreativeTabBuilder1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.WrappedCommand1_21;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.WrappedCommand1_21.INFO;
import static net.minecraft.core.registries.Registries.COMMAND_ARGUMENT_TYPE;
import static net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB;

public class TILCommonEntryPointNeoForge1_21 extends TILCommonEntryPoint1_21 {
    
    private static TILCommonEntryPointNeoForge1_21 INSTANCE;
    
    public static TILCommonEntryPointNeoForge1_21 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointNeoForge1_21();
    }
    
    public static void onRegisterCreativeTabs(RegisterEvent event) {
        if(event.getRegistryKey()==CREATIVE_MODE_TAB) CreativeTabBuilder1_21.onRegister(event);
        else if(event.getRegistryKey()==COMMAND_ARGUMENT_TYPE) {
            ResourceLocation registryName = TILRef.res("custom_suggester").unwrap();
            event.register(COMMAND_ARGUMENT_TYPE,registryName,() -> INFO);
            WrappedCommand1_21.registerArgType();
        }
    }
    
    public static void onSupplyCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        CreativeTabBuilder1_21.onSupply(event);
    }
    
    private TILCommonEntryPointNeoForge1_21() {
        INSTANCE = this;
    }
    
    @Nullable IEventBus getModBus() {
        if(Objects.isNull(this.extraData)) {
            TILRef.logWarn("(NeoForge 1.21) Extra data not found! Attempting to extract from context");
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
        if(Objects.nonNull(bus)) {
            TILRef.logError("Adding mod event listeners");
            bus.addListener(NetworkNeoForge1_21::registerPayloads);
            bus.addListener(TILCommonEntryPointNeoForge1_21::onRegisterCreativeTabs);
            bus.addListener(TILCommonEntryPointNeoForge1_21::onSupplyCreativeTabs);
        } else TILRef.logError("Failed to register creative tabs & network payloads!");
        super.onPreRegistration();
    }
}