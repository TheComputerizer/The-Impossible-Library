package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.client;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.client.NeoForgeClientHelpers;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.network.NetworkNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.TILClientEntryPoint1_21;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_POST;

public class TILClientEntryPointNeoForge1_21 extends TILClientEntryPoint1_21 {
    
    private static TILClientEntryPointNeoForge1_21 INSTANCE;
    
    public static TILClientEntryPointNeoForge1_21 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILClientEntryPointNeoForge1_21();
    }
    
    private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        TILRef.getClientHandles().registerKeyBindingsEvent(event);
    }
    
    private TILClientEntryPointNeoForge1_21() {
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
    
    @Override public void onClientSetup() {
        IEventBus bus = getModBus();
        if(Objects.nonNull(bus)) bus.addListener(NetworkNeoForge1_21::registerPayloadClient);
        else TILRef.logError("Failed to register network payloads!");
        EventHelper.addListener(RENDER_OVERLAY_POST,NeoForgeClientHelpers::emulateForgeDebugTextEvent);
    }
    
    @Override public void onPreRegistration() {
        IEventBus bus = getModBus();
        if(Objects.nonNull(bus)) bus.addListener(TILClientEntryPointNeoForge1_21::registerKeyMappings);
        else TILRef.logError("Failed to register keybinds!");
        super.onPreRegistration();
    }
}