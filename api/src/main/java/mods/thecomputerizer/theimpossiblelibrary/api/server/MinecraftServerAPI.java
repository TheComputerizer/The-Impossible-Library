package mods.thecomputerizer.theimpossiblelibrary.api.server;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

import javax.annotation.Nullable;
import java.io.File;
import java.util.List;

public abstract class MinecraftServerAPI<S> extends AbstractWrapped<S> {

    protected MinecraftServerAPI() {
        super(null);
    }

    @IndirectCallers public abstract void registerCommand(CommandAPI cmd);
    public abstract void executeCommandLiteral(String command);
    @IndirectCallers public abstract @Nullable PlayerAPI<?,?> getPlayerByUUID(String uuid);
    @IndirectCallers public abstract List<? extends PlayerAPI<?,?>> getPlayers();
    public abstract @Nullable File getSaveDir();
    public abstract S getServer();
    
    @Override public S getWrapped() {
        return getServer();
    }
}