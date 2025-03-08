package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m4.common;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.CommonForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m4.integration.ModHelperForge1_20_4;

import java.util.function.Supplier;

public class CommonForge1_20_4 extends CommonForge1_20 {
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_20_4(CoreAPI.getInstance().getSide());
    }
}