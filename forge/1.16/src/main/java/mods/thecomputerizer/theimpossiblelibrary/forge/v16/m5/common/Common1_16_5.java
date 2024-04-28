package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common;

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
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.Events1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.integration.ModHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.network.Network1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.RegistryHandler1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.Resource1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server.MinecraftServer1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.spawn.SpawnHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.tag.Tag1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text.TextHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.PosHelper1_16_5;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Objects;

public class Common1_16_5 implements CommonAPI {

    private EventsAPI events;
    private ModHelperAPI modHelper;
    private NetworkAPI<SimpleChannel,NetworkDirection> network;
    private PosHelperAPI<BlockPos> posHelper;
    private RegistryHandlerAPI registry;
    private ResourceAPI resource;
    private MinecraftServerAPI<MinecraftServer> server;
    private SpawnHelperAPI<LivingEntity> spawnHelper;
    private TagAPI tag;
    private TextHelperAPI<Style> textHelper;
    private WrapperAPI wrapper;


    @Override
    public void setUpBackendEntryPoint() {

    }

    @Override
    public EventsAPI getCommonEvents() {
        if(Objects.isNull(this.events)) this.events = new Events1_16_5();
        return this.events;
    }

    @Override
    public ModHelperAPI getModHelper() {
        if(Objects.isNull(this.modHelper)) this.modHelper = new ModHelper1_16_5(CoreAPI.INSTANCE.getSide());
        return this.modHelper;
    }

    @Override
    public NetworkAPI<SimpleChannel,NetworkDirection> getNetwork() {
        if(Objects.isNull(this.network)) this.network = new Network1_16_5();
        return this.network;
    }

    @Override
    public PosHelperAPI<BlockPos> getPosHelper() {
        if(Objects.isNull(this.posHelper)) this.posHelper = new PosHelper1_16_5();
        return this.posHelper;
    }

    @Override
    public RegistryHandlerAPI getRegistryHandler() {
        if(Objects.isNull(this.registry)) this.registry = new RegistryHandler1_16_5();
        return this.registry;
    }

    @Override
    public ResourceAPI getResource() {
        if(Objects.isNull(this.resource)) this.resource = new Resource1_16_5();
        return this.resource;
    }

    @Override
    public MinecraftServerAPI<MinecraftServer> getServer() {
        if(Objects.isNull(this.server)) this.server = new MinecraftServer1_16_5();
        return this.server;
    }

    @Override
    public SpawnHelperAPI<LivingEntity> getSpawnHelper() {
        if(Objects.isNull(this.spawnHelper)) this.spawnHelper = new SpawnHelper1_16_5();
        return this.spawnHelper;
    }

    @Override
    public TagAPI getTag() {
        if(Objects.isNull(this.tag)) this.tag = new Tag1_16_5();
        return this.tag;
    }

    @Override
    public TextHelperAPI<Style> getTextHelper() {
        if(Objects.isNull(this.textHelper)) this.textHelper = new TextHelper1_16_5();
        return this.textHelper;
    }

    @Override
    public WrapperAPI getWrapper() {
        if(Objects.isNull(this.wrapper)) this.wrapper = new Wrapper1_16_5();
        return this.wrapper;
    }
}
