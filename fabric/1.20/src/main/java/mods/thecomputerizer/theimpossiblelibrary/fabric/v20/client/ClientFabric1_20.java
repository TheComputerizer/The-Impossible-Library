package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.FabricHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.client.event.ClientEventsFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.event.CommonEventsFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.network.NetworkFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.server.MinecraftServerFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.server.event.ServerEventsFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.Client1_20;

import java.util.function.Supplier;

public abstract class ClientFabric1_20 extends Client1_20 {
    
    @Override protected Supplier<ClientEventsAPI> initClientEvents() {
        return ClientEventsFabric1_20::new;
    }
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsFabric1_20::new;
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkFabric1_20::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServerFabric1_20::new;
    }
    
    @Override public Supplier<ServerEventsAPI> initServerEvents() {
        return ServerEventsFabric1_20::new;
    }
    
    @Override protected Supplier<SharedHandlesClient> initSharedHandlesClient() {
        return FabricHandlesClient1_20::new;
    }
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return FabricHandlesCommon::new;
    }
}