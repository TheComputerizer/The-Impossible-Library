package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m2.client;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.client.ClientFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m2.common.event.CommonEventsFabric1_19_2;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m2.integration.ModHelperFabric1_19_2;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m2.registry.RegistryHandlerFabric1_19_2;

import java.util.function.Supplier;

public class ClientFabric1_19_2 extends ClientFabric1_19 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsFabric1_19_2::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperFabric1_19_2(getSide());
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerFabric1_19_2::new;
    }
}
