package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.CommonForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.event.CommonEventsForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.integration.ModHelperForge1_20_6;

import java.util.function.Supplier;

public class CommonForge1_20_6 extends CommonForge1_20 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsForge1_20_6::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_20_6(CoreAPI.getInstance().getSide());
    }
}