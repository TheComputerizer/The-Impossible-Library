package mods.thecomputerizer.theimpossiblelibrary.legacy.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import java.io.InputStream;

public class ResourceLegacy implements ResourceAPI {

    @Override
    public ResourceLocationLegacy getLocation(String path) {
        return null;
    }

    @Override
    public ResourceLocationLegacy getLocation(String modid, String path) {
        return null;
    }

    @Override
    public InputStream stream(ResourceLocationAPI<?> location) {
        return null;
    }
}
