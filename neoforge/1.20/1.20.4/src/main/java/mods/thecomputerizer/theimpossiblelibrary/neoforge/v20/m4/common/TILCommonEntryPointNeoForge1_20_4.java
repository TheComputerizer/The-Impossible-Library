package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.client.TILClientEntryPointNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.network.NetworkNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.common.TILCommonEntryPoint1_20_4;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TILCommonEntryPointNeoForge1_20_4 extends TILCommonEntryPoint1_20_4 {
    
    private static TILCommonEntryPointNeoForge1_20_4 INSTANCE;
    
    public static TILCommonEntryPointNeoForge1_20_4 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointNeoForge1_20_4();
    }
    
    private TILCommonEntryPointNeoForge1_20_4() {
        INSTANCE = this;
    }
    
    @Override public @Nullable ClientEntryPoint setDelegatedClientHandle() {
        return TILClientEntryPointNeoForge1_20_4.getInstance();
    }
    
    @Override public void onCommonSetup() {
        ModContainer container = ModLoadingContext.get().getActiveContainer();
        if(Objects.nonNull(container)) {
            IEventBus bus = container.getEventBus();
            if(Objects.nonNull(bus)) bus.addListener(NetworkNeoForge1_20_4::registerPayloadServer);
            else TILRef.logError("Failed to register network payloads! The event bus doesnt exist?");
        } else TILRef.logError("Failed to register network payloads! The mod container doesnt exist?");
        super.onCommonSetup();
    }
}