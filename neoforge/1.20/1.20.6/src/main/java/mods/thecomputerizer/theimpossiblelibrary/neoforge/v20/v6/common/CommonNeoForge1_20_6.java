package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v6.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.CommonNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v6.common.event.CommonEventsNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v6.integration.ModHelperNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v6.network.NetworkNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag.Tag1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.text.TextHelper1_20_6;

import java.util.function.Supplier;

public class CommonNeoForge1_20_6 extends CommonNeoForge1_20 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsNeoForge1_20_6::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperNeoForge1_20_6(CoreAPI.getInstance().getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkNeoForge1_20_6::new;
    }
    
    @Override public Supplier<TagAPI> initTag() {
        return Tag1_20_6::new;
    }
    
    @Override public Supplier<TextHelperAPI<?>> initTextHelper() {
        return TextHelper1_20_6::new;
    }
}