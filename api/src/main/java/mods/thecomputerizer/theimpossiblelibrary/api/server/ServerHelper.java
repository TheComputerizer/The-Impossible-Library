package mods.thecomputerizer.theimpossiblelibrary.api.server;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;

import javax.annotation.Nullable;
import java.util.Objects;

public class ServerHelper {

    public static @Nullable MinecraftServerAPI<?> getAPI() {
        return TILRef.getCommonSubAPI("MinecraftServerAPI",CommonAPI::getServerAPI);
    }

    @SuppressWarnings("unchecked")
    public static <S> @Nullable S getServer() {
        MinecraftServerAPI<?> api = getAPI();
        return Objects.nonNull(api) ? ((MinecraftServerAPI<S>)api).getServer() : null;
    }
}