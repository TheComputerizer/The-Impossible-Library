package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.ReferenceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.data.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;

public interface CommonAPI {

    TagAPI<?,?,?> getTagAPI();
    ReferenceAPI<?> getReferenceAPI();
    RegistryAPI getRegistryAPI();
}