package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.FabricHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.common.event.CommonEventsFabric1_21;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.network.NetworkFabric1_21;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.registry.RegistryHandlerFabric1_21;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.server.MinecraftServerFabric1_21;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.server.event.ServerEventsFabric1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.Common1_21;

import java.util.function.Supplier;

public abstract class CommonFabric1_21 extends Common1_21 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsFabric1_21::new;
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkFabric1_21::new;
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
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return FabricHandlesCommon::new;
    }
}