package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.BlockHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item.ToolHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.Resource1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server.CommandHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.spawn.SpawnHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.tag.Tag1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.PosHelper1_16_5;

import java.util.function.Supplier;

public abstract class Client1_16_5 extends ClientAPI {
    
    @Override public Supplier<BlockHelperAPI> initBlockHelper() {
        return BlockHelper1_16_5::new;
    }
    
    @Override public Supplier<CommandHelperAPI> initCommandHelper() {
        return CommandHelper1_16_5::new;
    }
    
    @Override public Supplier<PosHelperAPI<?>> initPosHelper() {
        return PosHelper1_16_5::new;
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
    
    @Override public Supplier<ToolHelperAPI> initToolHelper() {
        return ToolHelper1_16_5::new;
    }
}
