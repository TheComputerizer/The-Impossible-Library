package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client.gui.ScreenHelper1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client.input.KeyHelper1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client.sound.SoundHelper1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.block.BlockHelper1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.item.ToolHelper1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.resource.Resource1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.server.CommandHelper1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.spawn.SpawnHelper1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.tag.Tag1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.text.TextHelper1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.wrappers.Wrapper1_18_2;

import java.util.function.Supplier;

public abstract class Client1_18_2 extends ClientAPI {
    
    @Override public MinecraftAPI<?> getMinecraft() {
        return Minecraft1_18_2.getInstance();
    }
    
    @Override public Supplier<BlockHelperAPI> initBlockHelper() {
        return BlockHelper1_18_2::new;
    }
    
    @Override public Supplier<CommandHelperAPI> initCommandHelper() {
        return CommandHelper1_18_2::new;
    }
    
    @Override protected Supplier<KeyHelperAPI> initKeyHelper() {
        return KeyHelper1_18_2::new;
    }
    
    @Override public Supplier<ResourceAPI> initResource() {
        return Resource1_18_2::new;
    }
    
    @Override protected Supplier<ScreenHelperAPI> initScreenHelper() {
        return ScreenHelper1_18_2::new;
    }
    
    @Override protected Supplier<SoundHelperAPI> initSoundHelper() {
        return SoundHelper1_18_2::new;
    }
    
    @Override public Supplier<SpawnHelperAPI<?>> initSpawnHelper() {
        return SpawnHelper1_18_2::new;
    }
    
    @Override public Supplier<TagAPI> initTag() {
        return Tag1_18_2::new;
    }
    
    @Override public Supplier<TextHelperAPI<?>> initTextHelper() {
        return TextHelper1_18_2::new;
    }
    
    @Override public Supplier<ToolHelperAPI> initToolHelper() {
        return ToolHelper1_18_2::new;
    }
    
    @Override public Supplier<WrapperAPI> initWrapper() {
        return Wrapper1_18_2::new;
    }
}