package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m1.client;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.ClientForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m1.common.event.CommonEventsForge1_20_1;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m1.integration.ModHelperForge1_20_1;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m1.network.NetworkForge1_20_1;

import java.util.function.Supplier;

public class ClientForge1_20_1 extends ClientForge1_20 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsForge1_20_1::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_20_1(getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkForge1_20_1::new;
    }
}