package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.PosHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;

public interface CommonAPI {

    EventsAPI getCommonEventsAPI();
    ModAPI getModAPI();
    NetworkAPI<?,?> getNetworkAPI();
    PosHelperAPI<?> getPosHelperAPI();
    RegistryHandlerAPI<?> getRegistryHandlerAPI();
    ResourceAPI getResourceAPI();
    MinecraftServerAPI<?> getServerAPI();
    TagAPI getTagAPI();
    TextHelperAPI<?> getTextHelperAPI();
    WrapperAPI getWrapperAPI();
}