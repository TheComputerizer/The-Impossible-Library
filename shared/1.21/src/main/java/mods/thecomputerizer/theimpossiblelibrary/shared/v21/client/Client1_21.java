package mods.thecomputerizer.theimpossiblelibrary.shared.v21.client;

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
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.gui.ScreenHelper1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.input.KeyHelper1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.sound.SoundHelper1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.block.BlockHelper1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.item.ToolHelper1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.resource.Resource1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.CommandHelper1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.spawn.SpawnHelper1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.Tag1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.text.TextHelper1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.wrappers.Wrapper1_21;

import java.util.function.Supplier;

public abstract class Client1_21 extends ClientAPI {
    
    @Override public MinecraftAPI<?> getMinecraft() {
        return Minecraft1_21.getInstance();
    }
    
    @Override public Supplier<BlockHelperAPI> initBlockHelper() {
        return BlockHelper1_21::new;
    }
    
    @Override public Supplier<CommandHelperAPI> initCommandHelper() {
        return CommandHelper1_21::new;
    }
    
    @Override protected Supplier<KeyHelperAPI> initKeyHelper() {
        return KeyHelper1_21::new;
    }
    
    @Override public Supplier<ResourceAPI> initResource() {
        return Resource1_21::new;
    }
    
    @Override protected Supplier<ScreenHelperAPI> initScreenHelper() {
        return ScreenHelper1_21::new;
    }
    
    @Override protected Supplier<SoundHelperAPI> initSoundHelper() {
        return SoundHelper1_21::new;
    }
    
    @Override public Supplier<SpawnHelperAPI<?>> initSpawnHelper() {
        return SpawnHelper1_21::new;
    }
    
    @Override public Supplier<TagAPI> initTag() {
        return Tag1_21::new;
    }
    
    @Override public Supplier<TextHelperAPI<?>> initTextHelper() {
        return TextHelper1_21::new;
    }
    
    @Override public Supplier<ToolHelperAPI> initToolHelper() {
        return ToolHelper1_21::new;
    }
    
    @Override public Supplier<WrapperAPI> initWrapper() {
        return Wrapper1_21::new;
    }
}