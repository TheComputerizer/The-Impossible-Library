package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.m1.client;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.client.ClientNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.m1.integration.ModHelperNeoForge1_21_1;

import java.util.function.Supplier;

public class ClientNeoForge1_21_1 extends ClientNeoForge1_21 {
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperNeoForge1_21_1(CoreAPI.getInstance().getSide());
    }
}