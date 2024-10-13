package mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.FabricHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.common.event.CommonEventsFabric1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.integration.ModHelperFabric1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.network.NetworkFabric1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.registry.RegistryHandlerFabric1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.server.MinecraftServerFabric1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.server.event.ServerEventsFabric1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.Common1_18_2;

import java.util.function.Supplier;

public class CommonFabric1_18_2 extends Common1_18_2 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsFabric1_18_2::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperFabric1_18_2(CoreAPI.getInstance().getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkFabric1_18_2::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerFabric1_18_2::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServerFabric1_18_2::new;
    }
    
    @Override public Supplier<ServerEventsAPI> initServerEvents() {
        return ServerEventsFabric1_18_2::new;
    }
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return FabricHandlesCommon::new;
    }
}