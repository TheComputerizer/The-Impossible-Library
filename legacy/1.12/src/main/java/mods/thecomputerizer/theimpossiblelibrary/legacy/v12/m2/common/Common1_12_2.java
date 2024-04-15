package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.Events1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.integration.ModHelper1_12_2;
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

import javax.annotation.Nullable;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_BOTH;

public class Common1_12_2 implements CommonAPI {

    private final EventsAPI events;
    private final ModHelperAPI modHelper;
    private final NetworkAPI<SimpleNetworkWrapper,Side> network;
    private final PosHelperAPI<BlockPos> posHelper;
    private final RegistryHandlerAPI<Registry1_12_2<?>> registry;
    private final ResourceAPI resource;
    private final MinecraftServerAPI<MinecraftServer> server;
    private final SpawnHelperAPI<EntityLiving> spawnHelper;
    private final TagAPI tag;
    private final TextHelperAPI<Style> textHelper;
    private final WrapperAPI wrapper;

    public Common1_12_2(@Nullable CoreAPI core) {
        this.events = new Events1_12_2();
        this.modHelper = new ModHelper1_12_2(Objects.nonNull(core) ? core.getSide() : DEDICATED_BOTH);
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
    public EventsAPI getCommonEvents() {
        return this.events;
    }

    @Override
    public ModHelperAPI getModHelper() {
        return this.modHelper;
    }

    @Override
    public NetworkAPI<SimpleNetworkWrapper,Side> getNetwork() {
        return this.network;
    }

    @Override
    public PosHelperAPI<?> getPosHelper() {
        return this.posHelper;
    }

    @Override
    public RegistryHandlerAPI<Registry1_12_2<?>> getRegistryHandler() {
        return this.registry;
    }

    @Override
    public ResourceAPI getResource() {
        return this.resource;
    }

    @Override
    public MinecraftServerAPI<MinecraftServer> getServer() {
        return this.server;
    }

    @Override
    public SpawnHelperAPI<EntityLiving> getSpawnHelper() {
        return this.spawnHelper;
    }

    @Override
    public TagAPI getTag() {
        return this.tag;
    }

    @Override
    public TextHelperAPI<Style> getTextHelper() {
        return this.textHelper;
    }

    @Override
    public WrapperAPI getWrapper() {
        return this.wrapper;
    }
}
