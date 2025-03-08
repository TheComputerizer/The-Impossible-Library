package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.client;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.client.ClientFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.integration.ModHelperFabric1_20_1;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.registry.RegistryHandlerFabric1_20_1;

import java.util.function.Supplier;

public class ClientFabric1_20_1 extends ClientFabric1_20 {
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperFabric1_20_1(CoreAPI.getInstance().getSide());
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerFabric1_20_1::new;
    }
}