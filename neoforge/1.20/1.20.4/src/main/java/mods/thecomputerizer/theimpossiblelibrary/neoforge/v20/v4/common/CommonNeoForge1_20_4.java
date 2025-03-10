package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v4.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.CommonNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v4.common.event.CommonEventsNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v4.integration.ModHelperNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.tag.Tag1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.text.TextHelper1_20_4;

import java.util.function.Supplier;

public class CommonNeoForge1_20_4 extends CommonNeoForge1_20 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsNeoForge1_20_4::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperNeoForge1_20_4(CoreAPI.getInstance().getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkForge1_20_4::new;
    }
    
    @Override public Supplier<TagAPI> initTag() {
        return Tag1_20_4::new;
    }
    
    @Override public Supplier<TextHelperAPI<?>> initTextHelper() {
        return TextHelper1_20_4::new;
    }
}