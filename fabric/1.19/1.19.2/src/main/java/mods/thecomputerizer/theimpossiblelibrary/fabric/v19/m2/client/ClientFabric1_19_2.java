package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m2.client;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.client.ClientFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m2.integration.ModHelperFabric1_19_2;

import java.util.function.Supplier;

public class ClientFabric1_19_2 extends ClientFabric1_19 {
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperFabric1_19_2(CoreAPI.getInstance().getSide());
    }
}
