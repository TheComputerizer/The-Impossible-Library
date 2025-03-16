package mods.thecomputerizer.theimpossiblelibrary.forge.v21.m1.common;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v21.common.CommonForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.forge.v21.m1.integration.ModHelperForge1_21_1;

import java.util.function.Supplier;

public class CommonForge1_21_1 extends CommonForge1_21 {
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_21_1(CoreAPI.getInstance().getSide());
    }
}