package mods.thecomputerizer.theimpossiblelibrary.legacy.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.ModAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.EventsLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.network.NetworkLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.RegistryHandlerLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.RegistryLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.resource.ResourceLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.server.MinecraftServerLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.tag.TagLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.text.TextHelperLegacy;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonLegacy implements CommonAPI {

    private final EventsAPI events;
    private final ModAPI mod;
    private final NetworkAPI<SimpleNetworkWrapper,Side> network;
    private final RegistryHandlerAPI<RegistryLegacy<?>> registry;
    private final ResourceAPI resource;
    private final MinecraftServerAPI<MinecraftServer> server;
    private final TagAPI tag;
    private final TextHelperAPI<Style> textHelper;
    private final WrapperAPI wrapper;

    public CommonLegacy() {
        this.events = new EventsLegacy();
        this.mod = new ModLegacy();
        this.network = new NetworkLegacy();
        this.registry = new RegistryHandlerLegacy();
        this.resource = new ResourceLegacy();
        this.server = new MinecraftServerLegacy();
        this.tag = new TagLegacy();
        this.textHelper = new TextHelperLegacy();
        this.wrapper = new WrapperLegacy();
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
    public RegistryHandlerAPI<RegistryLegacy<?>> getRegistryHandlerAPI() {
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
