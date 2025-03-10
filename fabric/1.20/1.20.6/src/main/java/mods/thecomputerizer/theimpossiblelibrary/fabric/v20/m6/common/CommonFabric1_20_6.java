package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.CommonFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.common.event.CommonEventsFabric1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.integration.ModHelperFabric1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.network.NetworkFabric1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.registry.RegistryHandlerFabric1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag.Tag1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.text.TextHelper1_20_6;

import java.util.function.Supplier;

public class CommonFabric1_20_6 extends CommonFabric1_20 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsFabric1_20_6::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperFabric1_20_6(CoreAPI.getInstance().getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkFabric1_20_6::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerFabric1_20_6::new;
    }
    
    @Override public Supplier<TagAPI> initTag() {
        return Tag1_20_6::new;
    }
    
    @Override public Supplier<TextHelperAPI<?>> initTextHelper() {
        return TextHelper1_20_6::new;
    }
}