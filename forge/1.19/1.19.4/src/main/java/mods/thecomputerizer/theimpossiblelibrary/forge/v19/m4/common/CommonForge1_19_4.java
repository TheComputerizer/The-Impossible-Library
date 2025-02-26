package mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.common;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.common.CommonForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.integration.ModHelperForge1_19_4;

import java.util.function.Supplier;

public class CommonForge1_19_4 extends CommonForge1_19 {
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_19_4(CoreAPI.getInstance().getSide());
    }
}
