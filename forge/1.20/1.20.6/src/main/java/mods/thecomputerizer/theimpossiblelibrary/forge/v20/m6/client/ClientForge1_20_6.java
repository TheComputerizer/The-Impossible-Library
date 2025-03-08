package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.ClientForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.integration.ModHelperForge1_20_6;

import java.util.function.Supplier;

public class ClientForge1_20_6 extends ClientForge1_20 {
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_20_6(CoreAPI.getInstance().getSide());
    }
}