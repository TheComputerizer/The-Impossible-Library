package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.ClientForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.event.CommonEventsForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.integration.ModHelperForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.network.NetworkForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag.Tag1_20_6;

import java.util.function.Supplier;

public class ClientForge1_20_6 extends ClientForge1_20 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsForge1_20_6::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_20_6(CoreAPI.getInstance().getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkForge1_20_6::new;
    }
    
    @Override public Supplier<TagAPI> initTag() {
        return Tag1_20_6::new;
    }
}