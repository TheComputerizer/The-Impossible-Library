package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.FabricHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.event.CommonEventsFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.server.MinecraftServerFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.server.event.ServerEventsFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.Common1_20;

import java.util.function.Supplier;

public abstract class CommonFabric1_20 extends Common1_20 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsFabric1_20::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServerFabric1_20::new;
    }
    
    @Override public Supplier<ServerEventsAPI> initServerEvents() {
        return ServerEventsFabric1_20::new;
    }
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return FabricHandlesCommon::new;
    }
}