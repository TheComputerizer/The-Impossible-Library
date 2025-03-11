package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.client.ClientNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.common.event.CommonEventsNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.integration.ModHelperNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.network.NetworkNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.client.gui.ScreenHelper1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag.Tag1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.text.TextHelper1_20_6;

import java.util.function.Supplier;

public class ClientNeoForge1_20_6 extends ClientNeoForge1_20 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsNeoForge1_20_6::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperNeoForge1_20_6(CoreAPI.getInstance().getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkNeoForge1_20_6::new;
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
}