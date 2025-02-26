package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.FabricHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.FabricHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.client.event.ClientEventsFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common.event.CommonEventsFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.network.NetworkFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.registry.RegistryHandlerFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.server.MinecraftServerFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.server.event.ServerEventsFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.Client1_19;

import java.util.function.Supplier;

public abstract class ClientFabric1_19 extends Client1_19 {
    
    @Override protected Supplier<ClientEventsAPI> initClientEvents() {
        return ClientEventsFabric1_19::new;
    }
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsFabric1_19::new;
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkFabric1_19::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerFabric1_19::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServerFabric1_19::new;
    }
    
    @Override public Supplier<ServerEventsAPI> initServerEvents() {
        return ServerEventsFabric1_19::new;
    }
    
    @Override protected Supplier<SharedHandlesClient> initSharedHandlesClient() {
        return FabricHandlesClient::new;
    }
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return FabricHandlesCommon::new;
    }
}