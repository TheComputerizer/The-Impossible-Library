package mods.thecomputerizer.theimpossiblelibrary.api.server;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;

import javax.annotation.Nullable;
import java.util.Objects;

public class ServerHelper {

    public static void executeCommandLiteral(String command) {
        MinecraftServerAPI<?> server = getAPI();
        if(Objects.nonNull(server)) server.executeCommandLiteral(command);
    }

    public static @Nullable MinecraftServerAPI<?> getAPI() {
        return TILRef.getCommonSubAPI(CommonAPI::getServer);
    }

    @SuppressWarnings("unchecked")
    public static <S> @Nullable S getServer() {
        MinecraftServerAPI<?> api = getAPI();
        return Objects.nonNull(api) ? ((MinecraftServerAPI<S>)api).getServer() : null;
    }
}