package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.io.LogHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperAPI;

import java.util.Objects;
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.LOGGER;

public abstract class CommonAPI {
    
    private BlockHelperAPI blockHelper;
    private CommandHelperAPI commands;
    private CommonEventsAPI commonEvents;
    private CoreAPI core;
    private GameVersion version;
    private ModHelperAPI modHelper;
    private ModLoader modLoader;
    private NetworkAPI<?,?> network;
    private RegistryHandlerAPI registry;
    private ResourceAPI resource;
    private MinecraftServerAPI<?> server;
    private ServerEventsAPI serverEvents;
    private SharedHandlesCommon sharedHandles;
    private Side side;
    private SpawnHelperAPI<?> spawnHelper;
    private TagAPI tag;
    private TextHelperAPI<?> textHelper;
    private ToolHelperAPI toolHelper;
    private WrapperAPI wrapper;

    public final BlockHelperAPI getBlockHelper() {
        if(Objects.isNull(this.blockHelper)) this.blockHelper = safelySupply(initBlockHelper(),"BlockHelper");
        return this.blockHelper;
    }
    
    public final CommandHelperAPI getCommandHelper() {
        if(Objects.isNull(this.commands)) this.commands = safelySupply(initCommandHelper(),"CommandHelper");
        return this.commands;
    }
    
    public final CommonEventsAPI getCommonEvents() {
        if(Objects.isNull(this.commonEvents)) this.commonEvents = safelySupply(initCommonEvents(),"CommonEvents");
        return this.commonEvents;
    }
    
    public final CoreAPI getCore() {
        if(Objects.isNull(this.core)) this.core = safelySupply(CoreAPI::getInstance,"CoreAPI");
        return this.core;
    }
    
    public final ModHelperAPI getModHelper() {
        if(Objects.isNull(this.modHelper)) this.modHelper = safelySupply(initModHelper(),"ModHelper");
        return this.modHelper;
    }
    
    public final ModLoader getModLoader() {
        if(Objects.isNull(this.modLoader)) {
            CoreAPI core = getCore();
            if(Objects.isNull(core)) TILRef.logError("Failed to set ModLoader for CommonAPI with null CoreAPI!");
            else this.modLoader = safelySupply(core::getModLoader,"CoreAPI$ModLoader");
        }
        return this.modLoader;
    }
    
    public final NetworkAPI<?,?> getNetwork() {
        if(Objects.isNull(this.network)) this.network = safelySupply(initNetwork(),"Network");
        return this.network;
    }
    
    public final RegistryHandlerAPI getRegistryHandler() {
        if(Objects.isNull(this.registry)) this.registry = safelySupply(initRegistryHandler(),"RegistryHandler");
        return this.registry;
    }
    
    public final ResourceAPI getResource() {
        if(Objects.isNull(this.resource)) this.resource = safelySupply(initResource(),"Resource");
        return this.resource;
    }
    
    public final MinecraftServerAPI<?> getServer() {
        if(Objects.isNull(this.server)) this.server = safelySupply(initServer(),"MinecraftServer");
        return this.server;
    }
    
    public final ServerEventsAPI getServerEvents() {
        if(Objects.isNull(this.serverEvents)) this.serverEvents = safelySupply(initServerEvents(),"ServerEvents");
        return this.serverEvents;
    }
    
    public final SharedHandlesCommon getSharedHandlesCommon() {
        if(Objects.isNull(this.sharedHandles))
            this.sharedHandles = safelySupply(initSharedHandlesCommon(),"SharedHandlesCommon");
        return this.sharedHandles;
    }
    
    public final Side getSide() {
        if(Objects.isNull(this.side)) {
            CoreAPI core = getCore();
            if(Objects.isNull(core)) TILRef.logError("Failed to set Side for CommonAPI with null CoreAPI!");
            else this.side = safelySupply(core::getSide,"CoreAPI$Side");
        }
        return this.side;
    }
    
    public final SpawnHelperAPI<?> getSpawnHelper() {
        if(Objects.isNull(this.spawnHelper)) this.spawnHelper = safelySupply(initSpawnHelper(),"SpawnHelper");
        return this.spawnHelper;
    }
    
    public final TagAPI getTag() {
        if(Objects.isNull(this.tag)) this.tag = safelySupply(initTag(),"Tag");
        return this.tag;
    }
    
    public final TextHelperAPI<?> getTextHelper() {
        if(Objects.isNull(this.textHelper)) this.textHelper = safelySupply(initTextHelper(),"TextHelper");
        return this.textHelper;
    }
    
    public final ToolHelperAPI getToolHelper() {
        if(Objects.isNull(this.toolHelper)) this.toolHelper = safelySupply(initToolHelper(),"ToolHelper");
        return this.toolHelper;
    }
    
    public final GameVersion getVersion() {
        if(Objects.isNull(this.version)) {
            CoreAPI core = getCore();
            if(Objects.isNull(core)) TILRef.logError("Failed to set GameVersion for CommonAPI with null CoreAPI!");
            else this.version = safelySupply(core::getVersion,"CoreAPI$GameVersion");
        }
        return this.version;
    }
    
    public final WrapperAPI getWrapper() {
        if(Objects.isNull(this.wrapper)) this.wrapper = safelySupply(initWrapper(),"Wrapper");
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
    public abstract Supplier<SharedHandlesCommon> initSharedHandlesCommon();
    public abstract Supplier<SpawnHelperAPI<?>> initSpawnHelper();
    public abstract Supplier<TagAPI> initTag();
    public abstract Supplier<TextHelperAPI<?>> initTextHelper();
    public abstract Supplier<ToolHelperAPI> initToolHelper();
    public abstract Supplier<WrapperAPI> initWrapper();
    
    @SuppressWarnings("SameParameterValue")
    protected final String qualifyMsg(String msg) {
        String modLoader = String.valueOf(getModLoader()).toUpperCase();
        return "["+modLoader+" "+(this instanceof ClientAPI ? "CLIENT" : "COMMON")+" ("+getVersion()+")]: "+msg;
    }
    
    protected final <V> V safelySupply(Supplier<V> supplier, String type) {
        return Misc.safeSupplier(supplier,t -> {
            LogHelper.logFatalAndThrow(LOGGER,qualifyMsg("Failed to initialize helper of type '{}'"),t,type);
            return null; //unreachable
        }).get();
    }
}