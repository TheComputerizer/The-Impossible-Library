package mods.thecomputerizer.theimpossiblelibrary.api.server.event;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.events.ServerTickEventWrapper;

@Getter
public abstract class ServerEventWrapper<E> extends EventWrapper<E> {

    private MinecraftServerAPI<?> serverAPI;

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

    public ServerEventWrapper<?> setServerAPI(MinecraftServerAPI<?> api) {
        this.serverAPI = api;
        return this;
    }

    public static final class ServerType<E extends ServerEventWrapper<?>> extends EventType<E> {

        public static ServerType<ServerTickEventWrapper<?>> TICK_SERVER = new ServerType<>(false,false);

        private ServerType(boolean cancelable, boolean hasResult) {
            super(cancelable, hasResult);
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
    }
}