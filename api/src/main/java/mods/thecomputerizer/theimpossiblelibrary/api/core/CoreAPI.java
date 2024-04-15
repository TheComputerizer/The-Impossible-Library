package mods.thecomputerizer.theimpossiblelibrary.api.core;

import lombok.Getter;

import javax.annotation.Nullable;

@Getter
public abstract class CoreAPI { //TODO Actual core stuff

    private static CoreAPI INSTANCE;

    public static @Nullable CoreAPI getInstance() { //TODO Figure out coremod stuff to initialize the instance
        return INSTANCE;
    }

    protected final GameVersion version;
    protected final ModLoader loader;
    protected final Side side;

    protected CoreAPI(GameVersion version, ModLoader loader, Side side) {
        this.version = version;
        this.loader = loader;
        this.side = side;
        INSTANCE = this;
    }

    public abstract MultiLoaderAPI getLoader();

    public enum GameVersion { V12, V16, V18, V19, V20, V21 }
    public enum ModLoader { FABRIC, FORGE, LEGACY, NEOFORGE }

    @Getter
    public enum Side {
        ALL(true,true,true,true),
        DEDICATED_BOTH(true,true,true,false),
        DEDICATED_CLIENT(true,false,true,false),
        DEDICATED_SERVER(false,true,true,false),
        LOGICAL_BOTH(true,true,false,true),
        LOGICAL_CLIENT(true,false,false,true),
        LOGICAL_SERVER(false,true,false,true);

        private final boolean client;
        private final boolean server;
        private final boolean dedicated;
        private final boolean logical;

        Side(boolean client, boolean server, boolean dedicated, boolean logical) {
            this.client = client;
            this.server = server;
            this.dedicated = dedicated;
            this.logical = logical;
        }
    }
}