package mods.thecomputerizer.theimpossiblelibrary.api.server;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Objects;

public class ServerHelper {

    @IndirectCallers
    public static void executeCommandLiteral(String command) {
        MinecraftServerAPI<?> server = getAPI();
        if(Objects.nonNull(server)) server.executeCommandLiteral(command);
    }

    public static @Nullable MinecraftServerAPI<?> getAPI() {
        return TILRef.getCommonSubAPI(CommonAPI::getServer);
    }
    
    public static @Nullable File getSaveDir() {
        MinecraftServerAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.getSaveDir() : null;
    }

    public static <S> @Nullable S getServer() {
        MinecraftServerAPI<?> api = getAPI();
        return Objects.nonNull(api) ? api.unwrap() : null;
    }
}