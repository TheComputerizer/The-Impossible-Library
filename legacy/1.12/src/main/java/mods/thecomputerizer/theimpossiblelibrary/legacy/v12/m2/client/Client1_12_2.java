package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.ClientEvents1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.gui.ScreenHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.input.KeyHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.sound.SoundHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.Wrapper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.BlockHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.CommonEvents1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.ToolHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.integration.ModHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.network.Network1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.RegistryHandler1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.Resource1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server.CommandHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server.MinecraftServer1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.spawn.SpawnHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag.Tag1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.text.TextHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.PosHelper1_12_2;

import java.util.function.Supplier;

public class Client1_12_2 extends ClientAPI {

    @Override public MinecraftAPI getMinecraft() {
        return Minecraft1_12_2.getInstance();
    }
    
    @Override public Supplier<BlockHelperAPI> initBlockHelper() {
        return BlockHelper1_12_2::new;
    }
    
    @Override protected Supplier<ClientEventsAPI> initClientEvents() {
        return ClientEvents1_12_2::new;
    }
    
    @Override public Supplier<CommandHelperAPI> initCommandHelper() {
        return CommandHelper1_12_2::new;
    }
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEvents1_12_2::new;
    }
    
    @Override protected Supplier<KeyHelperAPI<?>> initKeyHelper() {
        return KeyHelper1_12_2::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelper1_12_2(CoreAPI.getInstance().getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return Network1_12_2::new;
    }
    
    @Override public Supplier<PosHelperAPI<?>> initPosHelper() {
        return PosHelper1_12_2::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandler1_12_2::new;
    }
    
    @Override public Supplier<ResourceAPI> initResource() {
        return Resource1_12_2::new;
    }
    
    @Override protected Supplier<ScreenHelperAPI> initScreenHelper() {
        return ScreenHelper1_12_2::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServer1_12_2::new;
    }
    
    @Override public Supplier<SpawnHelperAPI<?>> initSpawnHelper() {
        return SpawnHelper1_12_2::new;
    }
    
    @Override protected Supplier<SoundHelperAPI<?>> initSoundHelper() {
        return SoundHelper1_12_2::new;
    }
    
    @Override public Supplier<TagAPI> initTag() {
        return Tag1_12_2::new;
    }
    
    @Override public Supplier<TextHelperAPI<?>> initTextHelper() {
        return TextHelper1_12_2::new;
    }
    
    @Override public Supplier<ToolHelperAPI> initToolHelper() {
        return ToolHelper1_12_2::new;
    }
    
    @Override public Supplier<WrapperAPI> initWrapper() {
        return Wrapper1_12_2::new;
    }
}
