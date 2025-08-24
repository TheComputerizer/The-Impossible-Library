package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.client;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.TILClientEntryPoint1_20;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TILClientEntryPointNeoForge1_20_4 extends TILClientEntryPoint1_20 {
    
    private static TILClientEntryPointNeoForge1_20_4 INSTANCE;
    
    public static TILClientEntryPointNeoForge1_20_4 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILClientEntryPointNeoForge1_20_4();
    }
    
    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        TILRef.getClientHandles().registerKeyBindingsEvent(event);
    }
    
    private TILClientEntryPointNeoForge1_20_4() {
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
        if(Objects.nonNull(bus)) bus.addListener(TILClientEntryPointNeoForge1_20_4::registerKeyMappings);
        else TILRef.logError("Failed to register keybinds!");
        super.onPreRegistration();
    }
}