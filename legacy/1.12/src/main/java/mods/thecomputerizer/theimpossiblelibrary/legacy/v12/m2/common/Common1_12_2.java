package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.Events1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.integration.ModHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.network.Network1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.RegistryHandler1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.Resource1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server.MinecraftServer1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.spawn.SpawnHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag.Tag1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.text.TextHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.PosHelper1_12_2;
import net.minecraft.entity.EntityLiving;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Objects;

public class Common1_12_2 implements CommonAPI {

    private EventsAPI events;
    private ModHelperAPI modHelper;
    private NetworkAPI<SimpleNetworkWrapper,Side> network;
    private PosHelperAPI<BlockPos> posHelper;
    private RegistryHandlerAPI registry;
    private ResourceAPI resource;
    private MinecraftServerAPI<MinecraftServer> server;
    private SpawnHelperAPI<EntityLiving> spawnHelper;
    private TagAPI tag;
    private TextHelperAPI<Style> textHelper;
    private WrapperAPI wrapper;


    @Override
    public void setUpBackendEntryPoint() {

    }

    @Override
    public EventsAPI getCommonEvents() {
        if(Objects.isNull(this.events)) this.events = new Events1_12_2();
        return this.events;
    }

    @Override
    public ModHelperAPI getModHelper() {
        if(Objects.isNull(this.modHelper)) this.modHelper = new ModHelper1_12_2(CoreAPI.INSTANCE.getSide());
        return this.modHelper;
    }

    @Override
    public NetworkAPI<SimpleNetworkWrapper,Side> getNetwork() {
        if(Objects.isNull(this.network)) this.network = new Network1_12_2();
        return this.network;
    }

    @Override
    public PosHelperAPI<BlockPos> getPosHelper() {
        if(Objects.isNull(this.posHelper)) this.posHelper = new PosHelper1_12_2();
        return this.posHelper;
    }

    @Override
    public RegistryHandlerAPI getRegistryHandler() {
        if(Objects.isNull(this.registry)) this.registry = new RegistryHandler1_12_2();
        return this.registry;
    }

    @Override
    public ResourceAPI getResource() {
        if(Objects.isNull(this.resource)) this.resource = new Resource1_12_2();
        return this.resource;
    }

    @Override
    public MinecraftServerAPI<MinecraftServer> getServer() {
        if(Objects.isNull(this.server)) this.server = new MinecraftServer1_12_2();
        return this.server;
    }

    @Override
    public SpawnHelperAPI<EntityLiving> getSpawnHelper() {
        if(Objects.isNull(this.spawnHelper)) this.spawnHelper = new SpawnHelper1_12_2();
        return this.spawnHelper;
    }

    @Override
    public TagAPI getTag() {
        if(Objects.isNull(this.tag)) this.tag = new Tag1_12_2();
        return this.tag;
    }

    @Override
    public TextHelperAPI<Style> getTextHelper() {
        if(Objects.isNull(this.textHelper)) this.textHelper = new TextHelper1_12_2();
        return this.textHelper;
    }

    @Override
    public WrapperAPI getWrapper() {
        if(Objects.isNull(this.wrapper)) this.wrapper = new Wrapper1_12_2();
        return this.wrapper;
    }
}
