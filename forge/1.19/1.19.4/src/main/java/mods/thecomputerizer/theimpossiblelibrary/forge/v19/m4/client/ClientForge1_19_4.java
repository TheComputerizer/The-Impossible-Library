package mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.client.ClientForge1_19;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.common.event.CommonEventsForge1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.integration.ModHelperForge1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.registry.RegistryHandlerForge1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.client.Minecraft1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.client.gui.ScreenHelper1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.text.TextHelper1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.wrappers.Wrapper1_19_4;

import java.util.function.Supplier;

public class ClientForge1_19_4 extends ClientForge1_19 {
    
    @Override public MinecraftAPI<?> getMinecraft() {
        return Minecraft1_19_4.getInstance();
    }
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsForge1_19_4::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_19_4(CoreAPI.getInstance().getSide());
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerForge1_19_4::new;
    }
    
    @Override protected Supplier<ScreenHelperAPI> initScreenHelper() {
        return ScreenHelper1_19_4::new;
    }
    
    @Override public Supplier<TextHelperAPI<?>> initTextHelper() {
        return TextHelper1_19_4::new;
    }
    
    @Override public Supplier<WrapperAPI> initWrapper() {
        return Wrapper1_19_4::new;
    }
}
