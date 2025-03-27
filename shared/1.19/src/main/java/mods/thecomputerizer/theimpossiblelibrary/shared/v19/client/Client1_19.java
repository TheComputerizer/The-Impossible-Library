package mods.thecomputerizer.theimpossiblelibrary.shared.v19.client;

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
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.gui.ScreenHelper1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.input.KeyHelper1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.sound.SoundHelper1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.block.BlockHelper1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.item.ToolHelper1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.resource.Resource1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.server.CommandHelper1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.spawn.SpawnHelper1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.tag.Tag1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.text.TextHelper1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.wrappers.Wrapper1_19;

import java.util.function.Supplier;

public abstract class Client1_19 extends ClientAPI {
    
    @Override public Supplier<BlockHelperAPI> initBlockHelper() {
        return BlockHelper1_19::new;
    }
    
    @Override public Supplier<CommandHelperAPI> initCommandHelper() {
        return CommandHelper1_19::new;
    }
    
    @Override protected Supplier<KeyHelperAPI> initKeyHelper() {
        return KeyHelper1_19::new;
    }
    
    @Override public Supplier<ResourceAPI> initResource() {
        return Resource1_19::new;
    }
    
    @Override protected Supplier<ScreenHelperAPI> initScreenHelper() {
        return ScreenHelper1_19::new;
    }
    
    @Override protected Supplier<SoundHelperAPI> initSoundHelper() {
        return SoundHelper1_19::new;
    }
    
    @Override public Supplier<SpawnHelperAPI<?>> initSpawnHelper() {
        return SpawnHelper1_19::new;
    }
    
    @Override public Supplier<TagAPI> initTag() {
        return Tag1_19::new;
    }
    
    @Override public Supplier<TextHelperAPI<?>> initTextHelper() {
        return TextHelper1_19::new;
    }
    
    @Override public Supplier<ToolHelperAPI> initToolHelper() {
        return ToolHelper1_19::new;
    }
    
    @Override public Supplier<WrapperAPI> initWrapper() {
        return Wrapper1_19::new;
    }
    
    @Override public Supplier<MinecraftAPI<?>> minecraftGetter() {
        return Minecraft1_19::getInstance;
    }
}