package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;
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
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.BlockHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.CommonEvents1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.ToolHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.integration.ModHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.network.Network1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.RegistryHandler1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.Resource1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server.CommandHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server.MinecraftServer1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server.event.ServerEvents1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.spawn.SpawnHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag.Tag1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.text.TextHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.wrappers.Wrapper1_12_2;

import java.util.function.Supplier;

public class Common1_12_2 extends CommonAPI {
    
    @Override public Supplier<BlockHelperAPI> initBlockHelper() {
        return BlockHelper1_12_2::new;
    }
    
    @Override public Supplier<CommandHelperAPI> initCommandHelper() {
        return CommandHelper1_12_2::new;
    }
    
    @Override public Supplier<CommonEventsAPI> initCommonEvents() {
        return CommonEvents1_12_2::new;
    }
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelper1_12_2(CoreAPI.getInstance().getSide());
    }
    
    @Override public Supplier<NetworkAPI<?,?>> initNetwork() {
        return Network1_12_2::new;
    }
    
    @Override public Supplier<RegistryHandlerAPI> initRegistryHandler() {
        return RegistryHandler1_12_2::new;
    }
    
    @Override public Supplier<ResourceAPI> initResource() {
        return Resource1_12_2::new;
    }
    
    @Override public Supplier<MinecraftServerAPI<?>> initServer() {
        return MinecraftServer1_12_2::new;
    }
    
    @Override public Supplier<ServerEventsAPI> initServerEvents() {
        return ServerEvents1_12_2::new;
    }
    
    @Override public Supplier<SpawnHelperAPI<?>> initSpawnHelper() {
        return SpawnHelper1_12_2::new;
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