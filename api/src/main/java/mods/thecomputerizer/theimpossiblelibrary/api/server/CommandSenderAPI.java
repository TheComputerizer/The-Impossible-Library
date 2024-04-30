package mods.thecomputerizer.theimpossiblelibrary.api.server;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;

@Getter
public abstract class CommandSenderAPI<S> {

    protected final S sender;

    protected CommandSenderAPI(S sender) {
        this.sender = sender;
    }

    public abstract @Nullable EntityAPI<?,?> getEntity();
    public abstract String getName();
    public abstract @Nullable MinecraftServerAPI<?> getServer();
    public abstract WorldAPI<?> getWorld();
    public abstract void sendMessage(TextAPI<?> text);
}
