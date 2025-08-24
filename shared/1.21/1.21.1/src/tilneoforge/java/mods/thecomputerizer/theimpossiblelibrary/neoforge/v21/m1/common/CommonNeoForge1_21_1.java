package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.m1.common;

import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.common.CommonNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.m1.integration.ModHelperNeoForge1_21_1;

import java.util.function.Supplier;

public class CommonNeoForge1_21_1 extends CommonNeoForge1_21 {
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperNeoForge1_21_1(getSide());
    }
}