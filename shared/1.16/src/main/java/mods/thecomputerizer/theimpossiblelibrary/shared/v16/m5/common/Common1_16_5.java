package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.BlockHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item.ToolHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.Resource1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server.CommandHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.spawn.SpawnHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.tag.Tag1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.text.TextHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.wrappers.Wrapper1_16_5;

import java.util.function.Supplier;

public abstract class Common1_16_5 extends CommonAPI {
    
    @Override public Supplier<BlockHelperAPI> initBlockHelper() {
        return BlockHelper1_16_5::new;
    }
    
    @Override public Supplier<CommandHelperAPI> initCommandHelper() {
        return CommandHelper1_16_5::new;
    }
    
    @Override public Supplier<ResourceAPI> initResource() {
        return Resource1_16_5::new;
    }
    
    @Override public Supplier<SpawnHelperAPI<?>> initSpawnHelper() {
        return SpawnHelper1_16_5::new;
    }
    
    @Override public Supplier<TagAPI> initTag() {
        return Tag1_16_5::new;
    }
    
    @Override public Supplier<TextHelperAPI<?>> initTextHelper() {
        return TextHelper1_16_5::new;
    }
    
    @Override public Supplier<ToolHelperAPI> initToolHelper() {
        return ToolHelper1_16_5::new;
    }
    
    @Override public Supplier<WrapperAPI> initWrapper() {
        return Wrapper1_16_5::new;
    }
}