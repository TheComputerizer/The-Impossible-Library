package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.Events1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.spawn.SpawnHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.PosHelper1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.network.Network1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.RegistryHandler1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.Registry1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.Resource1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server.MinecraftServer1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag.Tag1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.text.TextHelper1_12_2;
import net.minecraft.entity.EntityLiving;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class Common1_12_2 implements CommonAPI {

    private final EventsAPI events;
    private final ModAPI mod;
    private final NetworkAPI<SimpleNetworkWrapper,Side> network;
    private final PosHelperAPI<BlockPos> posHelper;
    private final RegistryHandlerAPI<Registry1_12_2<?>> registry;
    private final ResourceAPI resource;
    private final MinecraftServerAPI<MinecraftServer> server;
    private final SpawnHelperAPI<EntityLiving> spawnHelper;
    private final TagAPI tag;
    private final TextHelperAPI<Style> textHelper;
    private final WrapperAPI wrapper;

    public Common1_12_2() {
        this.events = new Events1_12_2();
        this.mod = new Mod1_12_2();
        this.network = new Network1_12_2();
        this.posHelper = new PosHelper1_12_2();
        this.registry = new RegistryHandler1_12_2();
        this.resource = new Resource1_12_2();
        this.server = new MinecraftServer1_12_2();
        this.spawnHelper = new SpawnHelper1_12_2();
        this.tag = new Tag1_12_2();
        this.textHelper = new TextHelper1_12_2();
        this.wrapper = new Wrapper1_12_2();
    }


    @Override
    public EventsAPI getCommonEventsAPI() {
        return this.events;
    }

    @Override
    public ModAPI getModAPI() {
        return this.mod;
    }

    @Override
    public NetworkAPI<SimpleNetworkWrapper,Side> getNetworkAPI() {
        return this.network;
    }

    @Override
    public PosHelperAPI<?> getPosHelperAPI() {
        return this.posHelper;
    }

    @Override
    public RegistryHandlerAPI<Registry1_12_2<?>> getRegistryHandlerAPI() {
        return this.registry;
    }

    @Override
    public ResourceAPI getResourceAPI() {
        return this.resource;
    }

    @Override
    public MinecraftServerAPI<MinecraftServer> getServerAPI() {
        return this.server;
    }

    @Override
    public SpawnHelperAPI<EntityLiving> getSpawnHelperAPI() {
        return this.spawnHelper;
    }

    @Override
    public TagAPI getTagAPI() {
        return this.tag;
    }

    @Override
    public TextHelperAPI<Style> getTextHelperAPI() {
        return this.textHelper;
    }

    @Override
    public WrapperAPI getWrapperAPI() {
        return this.wrapper;
    }
}
