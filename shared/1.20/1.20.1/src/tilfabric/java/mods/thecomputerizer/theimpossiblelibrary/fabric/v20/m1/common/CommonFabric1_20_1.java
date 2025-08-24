package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.CommonFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.common.event.CommonEventsFabric1_20_1;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.integration.ModHelperFabric1_20_1;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.network.NetworkFabric1_20_1;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.registry.RegistryHandlerFabric1_20_1;

import java.util.function.Supplier;

public class CommonFabric1_20_1 extends CommonFabric1_20 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsFabric1_20_1::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperFabric1_20_1(getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkFabric1_20_1::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerFabric1_20_1::new;
    }
}