package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.ClientForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client.event.ClientEventsForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.common.event.CommonEventsForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.integration.ModHelperForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.network.NetworkForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.registry.RegistryHandlerForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.input.KeyHelper1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.sound.SoundHelper1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.item.ToolHelper1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.client.Minecraft1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.client.gui.ScreenHelper1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.common.block.BlockHelper1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag.Tag1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.text.TextHelper1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.wrappers.Wrapper1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.resource.Resource1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.server.CommandHelper1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.spawn.SpawnHelper1_20;

import java.util.function.Supplier;

public class ClientForge1_20_6 extends ClientForge1_20 {
    
    @Override public Supplier<BlockHelperAPI> initBlockHelper() {
        return BlockHelper1_20_6::new;
    }
    
    @Override public Supplier<ClientEventsAPI> initClientEvents() {
        return ClientEventsForge1_20_6::new;
    }
    
    @SuppressWarnings("RedundantMethodOverride")
    @Override public Supplier<CommandHelperAPI> initCommandHelper() {
        return CommandHelper1_20::new;
    }
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEventsForge1_20_6::new;
    }
    
    @SuppressWarnings("RedundantMethodOverride")
    @Override protected Supplier<KeyHelperAPI> initKeyHelper() {
        return KeyHelper1_20::new;
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
    
    @SuppressWarnings("RedundantMethodOverride")
    @Override public Supplier<ResourceAPI> initResource() {
        return Resource1_20::new;
    }
    
    @Override protected Supplier<ScreenHelperAPI> initScreenHelper() {
        return ScreenHelper1_20_6::new;
    }
    
    @SuppressWarnings("RedundantMethodOverride")
    @Override protected Supplier<SoundHelperAPI> initSoundHelper() {
        return SoundHelper1_20::new;
    }
    
    @SuppressWarnings("RedundantMethodOverride")
    @Override public Supplier<SpawnHelperAPI<?>> initSpawnHelper() {
        return SpawnHelper1_20::new;
    }
    
    @Override public Supplier<TagAPI> initTag() {
        return Tag1_20_6::new;
    }
    
    @SuppressWarnings("RedundantMethodOverride")
    @Override public Supplier<ToolHelperAPI> initToolHelper() {
        return ToolHelper1_20::new;
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