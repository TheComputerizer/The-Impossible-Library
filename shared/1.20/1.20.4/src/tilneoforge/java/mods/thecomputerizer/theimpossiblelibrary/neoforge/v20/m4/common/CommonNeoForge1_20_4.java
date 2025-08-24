package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.CommonNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event.CommonEventsNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.integration.ModHelperNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.network.NetworkNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.registry.RegistryHandlerNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.server.MinecraftServerNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.server.event.ServerEventsNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.tag.Tag1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.text.TextHelper1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.wrappers.Wrapper1_20_4;

import java.util.function.Supplier;

public class CommonNeoForge1_20_4 extends CommonNeoForge1_20 {
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsNeoForge1_20_4::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperNeoForge1_20_4(getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkNeoForge1_20_4::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerNeoForge1_20_4::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServerNeoForge1_20_4::new;
    }
    
    @Override public Supplier<ServerEventsAPI> initServerEvents() {
        return ServerEventsNeoForge1_20_4::new;
    }
    
    @Override public Supplier<TagAPI> initTag() {
        return Tag1_20_4::new;
    }
    
    @Override public Supplier<TextHelperAPI<?>> initTextHelper() {
        return TextHelper1_20_4::new;
    }
    
    @Override public Supplier<WrapperAPI> initWrapper() {
        return Wrapper1_20_4::new;
    }
}