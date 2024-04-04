package mods.thecomputerizer.theimpossiblelibrary.api.server.event;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventPriority;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventPriority.NORMAL;

@Getter
public abstract class ServerEventWrapper<S> extends EventWrapper {

    private MinecraftServerAPI<S> serverAPI;

    protected ServerEventWrapper(EventType<?> type) {
        super(type);
    }

    @Override
    public boolean isClient() {
        return false;
    }

    @Override
    public boolean isCommon() {
        return false;
    }

    @Override
    public boolean isServer() {
        return true;
    }

    public ServerEventWrapper<S> setServerAPI(MinecraftServerAPI<S> api) {
        this.serverAPI = api;
        return this;
    }
}