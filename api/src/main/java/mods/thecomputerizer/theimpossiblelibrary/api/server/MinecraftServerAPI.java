package mods.thecomputerizer.theimpossiblelibrary.api.server;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;

import javax.annotation.Nullable;
import java.io.File;
import java.util.List;

public abstract class MinecraftServerAPI<S> {

    protected MinecraftServerAPI() {}

    public abstract void registerCommand(CommandAPI cmd);
    public abstract void executeCommandLiteral(String command);
    public abstract @Nullable PlayerAPI<?,?> getPlayerByUUID(String uuid);
    public abstract List<PlayerAPI<?,?>> getPlayers();
    public abstract @Nullable File getSaveDir();
    public abstract S getServer();
}