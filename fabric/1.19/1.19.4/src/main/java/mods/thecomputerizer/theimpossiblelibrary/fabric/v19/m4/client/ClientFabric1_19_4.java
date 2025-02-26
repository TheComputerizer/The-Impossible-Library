package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.client;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.client.ClientFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.integration.ModHelperFabric1_19_4;

import java.util.function.Supplier;

public class ClientFabric1_19_4 extends ClientFabric1_19 {
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperFabric1_19_4(CoreAPI.getInstance().getSide());
    }
}
