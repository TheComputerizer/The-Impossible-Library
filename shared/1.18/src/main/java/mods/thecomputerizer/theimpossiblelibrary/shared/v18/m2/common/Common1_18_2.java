package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.block.BlockHelper1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.item.ToolHelper1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.resource.Resource1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.server.CommandHelper1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.spawn.SpawnHelper1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.tag.Tag1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.text.TextHelper1_18_2;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.wrappers.Wrapper1_18_2;

import java.util.function.Supplier;

public abstract class Common1_18_2 extends CommonAPI {
    
    @Override public Supplier<BlockHelperAPI> initBlockHelper() {
        return BlockHelper1_18_2::new;
    }
    
    @Override public Supplier<CommandHelperAPI> initCommandHelper() {
        return CommandHelper1_18_2::new;
    }
    
    @Override public Supplier<ResourceAPI> initResource() {
        return Resource1_18_2::new;
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