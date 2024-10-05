package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.ClientEventsForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.gui.ScreenHelperForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.input.KeyHelperForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.sound.SoundHelperForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.wrappers.WrapperForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.CommonEventsForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.integration.ModHelperForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.network.NetworkForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.RegistryHandlerForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server.MinecraftServerForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server.event.ServerEventsForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text.TextHelperForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.Client1_16_5;

import java.util.function.Supplier;

public class ClientForge1_16_5 extends Client1_16_5 {
    
    @Override public MinecraftAPI getMinecraft() {
        return MinecraftForge1_16_5.getInstance();
    }
    
    @Override protected Supplier<ClientEventsAPI> initClientEvents() {
        return ClientEventsForge1_16_5::new;
    }
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsForge1_16_5::new;
    }
    
    @Override protected Supplier<KeyHelperAPI<?>> initKeyHelper() {
        return KeyHelperForge1_16_5::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperForge1_16_5(CoreAPI.getInstance().getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return NetworkForge1_16_5::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandlerForge1_16_5::new;
    }
    
    @Override protected Supplier<ScreenHelperAPI> initScreenHelper() {
        return ScreenHelperForge1_16_5::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServerForge1_16_5::new;
    }
    
    @Override public Supplier<ServerEventsAPI> initServerEvents() {
        return ServerEventsForge1_16_5::new;
    }
    
    @Override protected Supplier<SoundHelperAPI<?>> initSoundHelper() {
        return SoundHelperForge1_16_5::new;
    }
    
    @Override public Supplier<TextHelperAPI<?>> initTextHelper() {
        return TextHelperForge1_16_5::new;
    }
    
    @Override public Supplier<WrapperAPI> initWrapper() {
        return WrapperForge1_16_5::new;
    }
}
