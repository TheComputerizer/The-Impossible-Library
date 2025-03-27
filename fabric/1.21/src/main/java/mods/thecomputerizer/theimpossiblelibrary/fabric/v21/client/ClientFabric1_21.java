package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.io.LogHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.FabricHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.client.event.ClientEventsFabric1_21;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.common.event.CommonEventsFabric1_21;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.network.NetworkFabric1_21;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.registry.RegistryHandlerFabric1_21;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.server.MinecraftServerFabric1_21;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.server.event.ServerEventsFabric1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.Client1_21;

import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.LOGGER;

public abstract class ClientFabric1_21 extends Client1_21 {
    
    @Override protected Supplier<ClientEventsAPI> initClientEvents() {
        return ClientEventsFabric1_21::new;
    }
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsFabric1_21::new;
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        TILRef.logInfo(qualifyMsg("Initializing network supplier"));
        try {
            return () -> {
                TILRef.logInfo(qualifyMsg("Constructing network"));
                try {
                    return new NetworkFabric1_21();
                } catch(Throwable t) {
                    LogHelper.logErrorAndThrow(LOGGER,qualifyMsg("Failed to construct network!"),t);
                    throw t;
                }
            };
        } catch(Throwable t) {
            LogHelper.logErrorAndThrow(LOGGER,qualifyMsg("Failed to initialize network supplier!"),t);
        }
        return () -> null; //unreachable
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerFabric1_21::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServerFabric1_21::new;
    }
    
    @Override public Supplier<ServerEventsAPI> initServerEvents() {
        return ServerEventsFabric1_21::new;
    }
    
    @Override protected Supplier<SharedHandlesClient> initSharedHandlesClient() {
        return FabricHandlesClient1_21::new;
    }
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return FabricHandlesCommon::new;
    }
}