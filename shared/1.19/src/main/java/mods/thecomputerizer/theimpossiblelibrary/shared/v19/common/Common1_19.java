package mods.thecomputerizer.theimpossiblelibrary.shared.v19.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.block.BlockHelper1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.item.ToolHelper1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.resource.Resource1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.server.CommandHelper1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.spawn.SpawnHelper1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.tag.Tag1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.text.TextHelper1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.wrappers.Wrapper1_19;

import java.util.function.Supplier;

public abstract class Common1_19 extends CommonAPI {
    
    @Override public Supplier<BlockHelperAPI> initBlockHelper() {
        return BlockHelper1_19::new;
    }
    
    @Override public Supplier<CommandHelperAPI> initCommandHelper() {
        return CommandHelper1_19::new;
    }
    
    @Override public Supplier<ResourceAPI> initResource() {
        return Resource1_19::new;
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
}