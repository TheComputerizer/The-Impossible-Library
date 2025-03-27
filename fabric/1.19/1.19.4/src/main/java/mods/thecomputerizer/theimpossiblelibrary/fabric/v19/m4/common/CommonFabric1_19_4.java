package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common.CommonFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.common.event.CommonEventsFabric1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.integration.ModHelperFabric1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.registry.RegistryHandlerFabric1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.text.TextHelper1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.wrappers.Wrapper1_19_4;

import java.util.function.Supplier;

public class CommonFabric1_19_4 extends CommonFabric1_19 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsFabric1_19_4::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperFabric1_19_4(getSide());
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerFabric1_19_4::new;
    }
    
    @Override public Supplier<TextHelperAPI<?>> initTextHelper() {
        return TextHelper1_19_4::new;
    }
    
    @Override public Supplier<WrapperAPI> initWrapper() {
        return Wrapper1_19_4::new;
    }
}
