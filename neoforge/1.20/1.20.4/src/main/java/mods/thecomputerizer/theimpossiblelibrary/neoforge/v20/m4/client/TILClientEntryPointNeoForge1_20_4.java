package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.client;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.client.NeoForgeClientHelpers;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.network.NetworkNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.TILClientEntryPoint1_20;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_POST;

public class TILClientEntryPointNeoForge1_20_4 extends TILClientEntryPoint1_20 {
    
    private static TILClientEntryPointNeoForge1_20_4 INSTANCE;
    
    public static TILClientEntryPointNeoForge1_20_4 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILClientEntryPointNeoForge1_20_4();
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
    
    @Override public void onClientSetup() {
        IEventBus bus = getModBus();
        if(Objects.nonNull(bus)) bus.addListener(NetworkNeoForge1_20_4::registerPayloadClient);
        else TILRef.logError("Failed to register network payloads!");
        EventHelper.addListener(RENDER_OVERLAY_POST,NeoForgeClientHelpers::emulateForgeDebugTextEvent);
        super.onCommonSetup();
    }
}