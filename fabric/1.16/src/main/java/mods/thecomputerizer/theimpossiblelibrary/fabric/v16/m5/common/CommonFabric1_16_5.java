package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.CommonEventsFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.integration.ModHelperFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.network.NetworkFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.registry.RegistryHandlerFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.server.MinecraftServerFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.server.event.ServerEventsFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.text.TextHelperFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.wrappers.WrapperFabric1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.Common1_16_5;

import java.util.function.Supplier;

public class CommonFabric1_16_5 extends Common1_16_5 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsFabric1_16_5::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperFabric1_16_5(CoreAPI.getInstance().getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkFabric1_16_5::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerFabric1_16_5::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServerFabric1_16_5::new;
    }
    
    @Override public Supplier<ServerEventsAPI> initServerEvents() {
        return ServerEventsFabric1_16_5::new;
    }
    
    @Override public Supplier<TextHelperAPI<?>> initTextHelper() {
        return TextHelperFabric1_16_5::new;
    }
    
    @Override public Supplier<WrapperAPI> initWrapper() {
        return WrapperFabric1_16_5::new;
    }
}
