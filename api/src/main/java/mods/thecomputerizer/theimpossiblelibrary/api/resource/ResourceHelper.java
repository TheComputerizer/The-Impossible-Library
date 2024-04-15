package mods.thecomputerizer.theimpossiblelibrary.api.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Objects;

public class ResourceHelper {

    public static @Nullable ResourceAPI getResourceAPI() {
        return TILRef.getCommonSubAPI(CommonAPI::getResource);
    }

    public static @Nullable ResourceLocationAPI<?> getResource(String path) {
        ResourceAPI api = getResourceAPI();
        return Objects.nonNull(api) ? api.getLocation(path) : null;
    }

    public static @Nullable ResourceLocationAPI<?> getResource(String modid, String path) {
        ResourceAPI api = getResourceAPI();
        return Objects.nonNull(api) ? api.getLocation(modid,path) : null;

    }

    public static @Nullable InputStream getResourceStream(String path) {
        return getResourceStream(getResource(path));
    }

    public static @Nullable InputStream getResourceStream(String modid, String path) {
        return getResourceStream(getResource(modid,path));
    }

    public static @Nullable InputStream getResourceStream(@Nullable ResourceLocationAPI<?> location) {
        if(Objects.isNull(location)) return null;
        ResourceAPI api = getResourceAPI();
        return Objects.nonNull(api) ? api.stream(location) : null;
    }
}