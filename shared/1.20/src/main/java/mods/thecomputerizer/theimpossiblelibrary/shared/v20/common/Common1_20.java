package mods.thecomputerizer.theimpossiblelibrary.shared.v20.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.block.BlockHelper1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.item.ToolHelper1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.resource.Resource1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.server.CommandHelper1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.spawn.SpawnHelper1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.tag.Tag1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.text.TextHelper1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.wrappers.Wrapper1_20;

import java.util.function.Supplier;

public abstract class Common1_20 extends CommonAPI {
    
    @Override public Supplier<BlockHelperAPI> initBlockHelper() {
        return BlockHelper1_20::new;
    }
    
    @Override public Supplier<CommandHelperAPI> initCommandHelper() {
        return CommandHelper1_20::new;
    }
    
    @Override public Supplier<ResourceAPI> initResource() {
        return Resource1_20::new;
    }
    
    @Override public Supplier<SpawnHelperAPI<?>> initSpawnHelper() {
        return SpawnHelper1_20::new;
    }
    
    @Override public Supplier<TagAPI> initTag() {
        return Tag1_20::new;
    }
    
    @Override public Supplier<TextHelperAPI<?>> initTextHelper() {
        return TextHelper1_20::new;
    }
    
    @Override public Supplier<ToolHelperAPI> initToolHelper() {
        return ToolHelper1_20::new;
    }
    
    @Override public Supplier<WrapperAPI> initWrapper() {
        return Wrapper1_20::new;
    }
}