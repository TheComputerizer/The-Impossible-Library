package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;

import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.ClientForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.common.event.CommonEventsForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.integration.ModHelperForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.network.NetworkForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.registry.RegistryHandlerForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.client.Minecraft1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.client.gui.ScreenHelper1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag.Tag1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.text.TextHelper1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.wrappers.Wrapper1_20_6;

import java.util.function.Supplier;

public class ClientForge1_20_6 extends ClientForge1_20 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsForge1_20_6::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_20_6(getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkForge1_20_6::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerForge1_20_6::new;
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
    
    @Override public Supplier<MinecraftAPI<?>> minecraftGetter() {
        return Minecraft1_20_6::getInstance;
    }
}