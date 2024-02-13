package mods.thecomputerizer.theimpossiblelibrary.api.resource;

import java.io.InputStream;

public interface ResourceAPI {

    ResourceLocationAPI<?> getLocation(String path);
    ResourceLocationAPI<?> getLocation(String modid, String path);
    InputStream stream(ResourceLocationAPI<?> location);
}
