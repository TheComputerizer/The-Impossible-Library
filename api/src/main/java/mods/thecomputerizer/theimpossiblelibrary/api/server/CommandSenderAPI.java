package mods.thecomputerizer.theimpossiblelibrary.api.server;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;

public abstract class CommandSenderAPI<S> extends AbstractWrapped<S> {

    protected CommandSenderAPI(S sender) {
        super(sender);
    }

    public abstract @Nullable EntityAPI<?,?> getEntity();
    public abstract String getName();
    
    public @Nullable MinecraftServerAPI<?> getServer() {
        return ServerHelper.getAPI();
    }
    
    public abstract WorldAPI<?> getWorld();
    @IndirectCallers public abstract void sendMessage(TextAPI<?> text);
}