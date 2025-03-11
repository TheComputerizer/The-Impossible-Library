package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v4.client;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v4.network.NetworkNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.TILClientEntryPoint1_20;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;

import java.util.Objects;

public class TILClientEntryPointNeoForge1_20_4 extends TILClientEntryPoint1_20 {
    
    private static TILClientEntryPointNeoForge1_20_4 INSTANCE;
    
    public static TILClientEntryPointNeoForge1_20_4 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILClientEntryPointNeoForge1_20_4();
    }
    
    private TILClientEntryPointNeoForge1_20_4() {
        INSTANCE = this;
    }
    
    @Override public void onClientSetup() {
        ModContainer container = ModLoadingContext.get().getActiveContainer();
        if(Objects.nonNull(container)) {
            IEventBus bus = container.getEventBus();
            if(Objects.nonNull(bus)) bus.addListener(NetworkNeoForge1_20_4::registerPayloadClient);
            else TILRef.logError("Failed to register network payloads! The event bus doesnt exist?");
        } else TILRef.logError("Failed to register network payloads! The mod container doesnt exist?");
    }
}