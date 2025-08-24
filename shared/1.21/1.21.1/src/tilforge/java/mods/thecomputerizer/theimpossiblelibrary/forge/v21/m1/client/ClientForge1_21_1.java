package mods.thecomputerizer.theimpossiblelibrary.forge.v21.m1.client;

import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v21.client.ClientForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.forge.v21.m1.integration.ModHelperForge1_21_1;

import java.util.function.Supplier;

public class ClientForge1_21_1 extends ClientForge1_21 {
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_21_1(getSide());
    }
}