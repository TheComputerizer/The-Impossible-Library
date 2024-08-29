package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.CommonEventsForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.integration.ModHelperForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.network.NetworkForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.RegistryHandlerForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.Common1_16_5;

import java.util.function.Supplier;

public class CommonForge1_16_5 extends Common1_16_5 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsForge1_16_5::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_16_5(CoreAPI.getInstance().getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkForge1_16_5::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerForge1_16_5::new;
    }
    
    @Override public Supplier<WrapperAPI> initWrapper() {
        return WrapperForge1_16_5::new;
    }
}
