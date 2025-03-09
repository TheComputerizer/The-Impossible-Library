package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m4.client;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.client.ClientFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m4.event.CommonEventsFabric1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m4.integration.ModHelperFabric1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m4.network.NetworkFabric1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m4.registry.RegistryHandlerFabric1_20_4;

import java.util.function.Supplier;

public class ClientFabric1_20_4 extends ClientFabric1_20 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsFabric1_20_4::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperFabric1_20_4(CoreAPI.getInstance().getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkFabric1_20_4::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerFabric1_20_4::new;
    }
}