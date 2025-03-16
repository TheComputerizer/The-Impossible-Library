package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.client.ClientFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.common.event.CommonEventsFabric1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.integration.ModHelperFabric1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.network.NetworkFabric1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m6.registry.RegistryHandlerFabric1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.client.gui.ScreenHelper1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag.Tag1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.text.TextHelper1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.wrappers.Wrapper1_20_6;

import java.util.function.Supplier;

public class ClientFabric1_20_6 extends ClientFabric1_20 {
    
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
    
    @Override protected Supplier<ScreenHelperAPI> initScreenHelper() {
        return ScreenHelper1_20_6::new;
    }
    
    @Override public Supplier<TagAPI> initTag() {
        return Tag1_20_6::new;
    }
    
    @Override public Supplier<TextHelperAPI<?>> initTextHelper() {
        return TextHelper1_20_6::new;
    }
    
    @Override public Supplier<WrapperAPI> initWrapper() {
        return Wrapper1_20_6::new;
    }
}