package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.client.TILClientEntryPointNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.network.NetworkNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.common.TILCommonEntryPoint1_20_6;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TILCommonEntryPointNeoForge1_20_6 extends TILCommonEntryPoint1_20_6 {
    
    private static TILCommonEntryPointNeoForge1_20_6 INSTANCE;
    
    public static TILCommonEntryPointNeoForge1_20_6 getInstance() {
        return Objects.nonNull(INSTANCE) ? INSTANCE : new TILCommonEntryPointNeoForge1_20_6();
    }
    
    private TILCommonEntryPointNeoForge1_20_6() {
        INSTANCE = this;
    }
    
    @Override public @Nullable ClientEntryPoint setDelegatedClientHandle() {
        return TILClientEntryPointNeoForge1_20_6.getInstance();
    }
    
    @Override public void onCommonSetup() {
        ModContainer container = ModLoadingContext.get().getActiveContainer();
        if(Objects.nonNull(container)) {
            IEventBus bus = container.getEventBus();
            if(Objects.nonNull(bus)) bus.addListener(NetworkNeoForge1_20_6::registerPayloadServer);
            else TILRef.logError("Failed to register network payloads! The event bus doesnt exist?");
        } else TILRef.logError("Failed to register network payloads! The mod container doesnt exist?");
        super.onCommonSetup();
    }
}