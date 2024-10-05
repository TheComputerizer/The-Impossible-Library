package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolHelperAPI;
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
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class CommonAPI {
    
    private BlockHelperAPI blockHelper;
    private CommandHelperAPI commands;
    private CommonEventsAPI commonEvents;
    private ModHelperAPI modHelper;
    private NetworkAPI<?,?> network;
    private RegistryHandlerAPI registry;
    private ResourceAPI resource;
    private MinecraftServerAPI<?> server;
    private ServerEventsAPI serverEvents;
    private SpawnHelperAPI<?> spawnHelper;
    private TagAPI tag;
    private TextHelperAPI<?> textHelper;
    private ToolHelperAPI toolHelper;
    private WrapperAPI wrapper;

    public BlockHelperAPI getBlockHelper() {
        if(Objects.isNull(this.blockHelper)) this.blockHelper = initBlockHelper().get();
        return this.blockHelper;
    }
    
    public CommandHelperAPI getCommandHelper() {
        if(Objects.isNull(this.commands)) this.commands = initCommandHelper().get();
        return this.commands;
    }
    
    public CommonEventsAPI getCommonEvents() {
        if(Objects.isNull(this.commonEvents)) this.commonEvents = initCommonEvents().get();
        return this.commonEvents;
    }
    
    public ModHelperAPI getModHelper() {
        if(Objects.isNull(this.modHelper)) this.modHelper = initModHelper().get();
        return this.modHelper;
    }
    
    public NetworkAPI<?,?> getNetwork() {
        if(Objects.isNull(this.network)) this.network = initNetwork().get();
        return this.network;
    }
    
    public RegistryHandlerAPI getRegistryHandler() {
        if(Objects.isNull(this.registry)) this.registry = initRegistryHandler().get();
        return this.registry;
    }
    
    public ResourceAPI getResource() {
        if(Objects.isNull(this.resource)) this.resource = initResource().get();
        return this.resource;
    }
    
    public MinecraftServerAPI<?> getServer() {
        if(Objects.isNull(this.server)) this.server = initServer().get();
        return this.server;
    }
    
    public ServerEventsAPI getServerEvents() {
        if(Objects.isNull(this.serverEvents)) this.serverEvents = initServerEvents().get();
        return this.serverEvents;
    }
    
    public SpawnHelperAPI<?> getSpawnHelper() {
        if(Objects.isNull(this.spawnHelper)) this.spawnHelper = initSpawnHelper().get();
        return this.spawnHelper;
    }
    
    public TagAPI getTag() {
        if(Objects.isNull(this.tag)) this.tag = initTag().get();
        return this.tag;
    }
    
    public TextHelperAPI<?> getTextHelper() {
        if(Objects.isNull(this.textHelper)) this.textHelper = initTextHelper().get();
        return this.textHelper;
    }
    
    public ToolHelperAPI getToolHelper() {
        if(Objects.isNull(this.toolHelper)) this.toolHelper = initToolHelper().get();
        return this.toolHelper;
    }
    
    public WrapperAPI getWrapper() {
        if(Objects.isNull(this.wrapper)) this.wrapper = initWrapper().get();
        return this.wrapper;
    }
    
    public abstract Supplier<BlockHelperAPI> initBlockHelper();
    public abstract Supplier<CommandHelperAPI> initCommandHelper();
    public abstract Supplier<CommonEventsAPI> initCommonEvents();
    public abstract Supplier<ModHelperAPI> initModHelper();
    public abstract Supplier<NetworkAPI<?,?>> initNetwork();
    public abstract Supplier<RegistryHandlerAPI> initRegistryHandler();
    public abstract Supplier<ResourceAPI> initResource();
    public abstract Supplier<MinecraftServerAPI<?>> initServer();
    public abstract Supplier<ServerEventsAPI> initServerEvents();
    public abstract Supplier<SpawnHelperAPI<?>> initSpawnHelper();
    public abstract Supplier<TagAPI> initTag();
    public abstract Supplier<TextHelperAPI<?>> initTextHelper();
    public abstract Supplier<ToolHelperAPI> initToolHelper();
    public abstract Supplier<WrapperAPI> initWrapper();
}