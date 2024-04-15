package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common;

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
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.Events1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.integration.ModHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.spawn.SpawnHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.PosHelper1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.network.Network1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.Registry1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.RegistryHandler1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.Resource1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server.MinecraftServer1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.tag.Tag1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text.TextHelper1_16_5;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import javax.annotation.Nullable;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_BOTH;

public class Common1_16_5 implements CommonAPI {

    private final EventsAPI events;
    private final ModHelperAPI modHelper;
    private final NetworkAPI<SimpleChannel,NetworkDirection> network;
    private final PosHelperAPI<BlockPos> posHelper;
    private final RegistryHandlerAPI<Registry1_16_5<?>> registry;
    private final ResourceAPI resource;
    private final MinecraftServerAPI<MinecraftServer> server;
    private final SpawnHelperAPI<LivingEntity> spawnHelper;
    private final TagAPI tag;
    private final TextHelperAPI<Style> textHelper;
    private final WrapperAPI wrapper;

    public Common1_16_5(@Nullable CoreAPI core) {
        this.events = new Events1_16_5();
        this.modHelper = new ModHelper1_16_5(Objects.nonNull(core) ? core.getSide() : DEDICATED_BOTH);
        this.network = new Network1_16_5();
        this.posHelper = new PosHelper1_16_5();
        this.registry = new RegistryHandler1_16_5();
        this.resource = new Resource1_16_5();
        this.server = new MinecraftServer1_16_5();
        this.spawnHelper = new SpawnHelper1_16_5();
        this.tag = new Tag1_16_5();
        this.textHelper = new TextHelper1_16_5();
        this.wrapper = new Wrapper1_16_5();
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
    public NetworkAPI<SimpleChannel,NetworkDirection> getNetwork() {
        return this.network;
    }

    @Override
    public PosHelperAPI<BlockPos> getPosHelper() {
        return this.posHelper;
    }

    @Override
    public RegistryHandlerAPI<Registry1_16_5<?>> getRegistryHandler() {
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
    public SpawnHelperAPI<LivingEntity> getSpawnHelper() {
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
