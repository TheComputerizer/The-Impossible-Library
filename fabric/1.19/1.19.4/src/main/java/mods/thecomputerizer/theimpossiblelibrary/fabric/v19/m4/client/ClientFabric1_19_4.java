package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.client.ClientFabric1_19;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.integration.ModHelperFabric1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.registry.RegistryHandlerFabric1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.client.Minecraft1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.client.gui.ScreenHelper1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.text.TextHelper1_19_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.wrappers.Wrapper1_19_4;

import java.util.function.Supplier;

public class ClientFabric1_19_4 extends ClientFabric1_19 {
    
    @Override public MinecraftAPI<?> getMinecraft() {
        return Minecraft1_19_4.getInstance();
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperFabric1_19_4(CoreAPI.getInstance().getSide());
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerFabric1_19_4::new;
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
