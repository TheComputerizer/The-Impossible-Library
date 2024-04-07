package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.PosHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.EventsForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.world.PosHelperForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.network.NetworkForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.RegistryForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.RegistryHandlerForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.resource.ResourceForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.server.MinecraftServerForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.tag.TagForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.text.TextHelperForge;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class CommonForge implements CommonAPI {

    private final EventsAPI events;
    private final ModAPI mod;
    private final NetworkAPI<SimpleChannel,NetworkDirection> network;
    private final PosHelperAPI<BlockPos> posHelper;
    private final RegistryHandlerAPI<RegistryForge<?>> registry;
    private final ResourceAPI resource;
    private final MinecraftServerAPI<MinecraftServer> server;
    private final TagAPI tag;
    private final TextHelperAPI<Style> textHelper;
    private final WrapperAPI wrapper;

    public CommonForge() {
        this.events = new EventsForge();
        this.mod = new ModForge();
        this.network = new NetworkForge();
        this.posHelper = new PosHelperForge();
        this.registry = new RegistryHandlerForge();
        this.resource = new ResourceForge();
        this.server = new MinecraftServerForge();
        this.tag = new TagForge();
        this.textHelper = new TextHelperForge();
        this.wrapper = new WrapperForge();
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
    public NetworkAPI<SimpleChannel,NetworkDirection> getNetworkAPI() {
        return this.network;
    }

    @Override
    public PosHelperAPI<BlockPos> getPosHelperAPI() {
        return this.posHelper;
    }

    @Override
    public RegistryHandlerAPI<RegistryForge<?>> getRegistryHandlerAPI() {
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
