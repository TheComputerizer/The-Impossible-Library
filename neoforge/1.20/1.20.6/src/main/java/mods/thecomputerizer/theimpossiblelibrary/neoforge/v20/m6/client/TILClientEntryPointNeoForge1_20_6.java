package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.client;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.network.NetworkNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.TILClientEntryPoint1_20;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;

import java.util.Objects;

public class TILClientEntryPointNeoForge1_20_6 extends TILClientEntryPoint1_20 {
    
    private static TILClientEntryPointNeoForge1_20_6 INSTANCE;
    
    public static TILClientEntryPointNeoForge1_20_6 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILClientEntryPointNeoForge1_20_6();
    }
    
    private TILClientEntryPointNeoForge1_20_6() {
        INSTANCE = this;
    }
    
    @Override public void onClientSetup() {
        if(Objects.isNull(this.extraData)) {
            TILRef.logWarn("(NeoForge 1.20.6) Extra data not found! Attempting to extract from context");
            ModContainer container = ModLoadingContext.get().getActiveContainer();
            if(Objects.nonNull(container)) this.extraData = container.getEventBus();
            else TILRef.logError("Failed to set extra data! The mod container doesnt exist?");
        }
        if(this.extraData instanceof IEventBus)
            ((IEventBus)this.extraData).addListener(NetworkNeoForge1_20_6::registerPayloadServer);
        else TILRef.logError("Failed to register network payloads! "+
                             "Extra data not set to instance of IEventBus {}",this.extraData);
    }
}