package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m4.client;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.ClientForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m4.event.CommonEventsForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m4.integration.ModHelperForge1_20_4;

import java.util.function.Supplier;

public class ClientForge1_20_4 extends ClientForge1_20 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsForge1_20_4::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_20_4(CoreAPI.getInstance().getSide());
    }
}