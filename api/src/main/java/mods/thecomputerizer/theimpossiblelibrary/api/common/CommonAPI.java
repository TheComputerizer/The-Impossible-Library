package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.spawn.SpawnHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelperAPI;

public interface CommonAPI {

    void setUpBackendEntryPoint();
    EventsAPI getCommonEvents();
    ModHelperAPI getModHelper();
    NetworkAPI<?,?> getNetwork();
    PosHelperAPI<?> getPosHelper();
    RegistryHandlerAPI getRegistryHandler();
    ResourceAPI getResource();
    MinecraftServerAPI<?> getServer();
    SpawnHelperAPI<?> getSpawnHelper();
    TagAPI getTag();
    TextHelperAPI<?> getTextHelper();
    WrapperAPI getWrapper();
}