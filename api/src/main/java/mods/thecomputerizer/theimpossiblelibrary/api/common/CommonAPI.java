package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHandlerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;

public interface CommonAPI {

    CommonEventsAPI getCommonEventsAPI();
    ModAPI getModAPI();
    NetworkAPI<?,?> getNetworkAPI();
    Reference getReference();
    RegistryHandlerAPI<?> getRegistryHandlerAPI();
    ResourceAPI getResourceAPI();
    TagAPI getTagAPI();
}