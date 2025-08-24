package mods.thecomputerizer.theimpossiblelibrary.forge.v19.m2.client;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.client.ClientForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m2.common.event.CommonEventsForge1_19_2;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m2.integration.ModHelperForge1_19_2;

import java.util.function.Supplier;

public class ClientForge1_19_2 extends ClientForge1_19 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsForge1_19_2::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_19_2(getSide());
    }
}
