package mods.thecomputerizer.theimpossiblelibrary.forge.v19.m2.client;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.client.ClientForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m2.integration.ModHelperForge1_19_2;

import java.util.function.Supplier;

public class ClientForge1_19_2 extends ClientForge1_19 {
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_19_2(CoreAPI.getInstance().getSide());
    }
}
