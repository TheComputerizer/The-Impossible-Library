package mods.thecomputerizer.theimpossiblelibrary.api.core;

public abstract class CoreAPI { //TODO Actual core stuff

    private static CoreAPI INSTANCE;

    private final GameVersion version;
    private final ModLoader loader;

    protected CoreAPI(GameVersion version, ModLoader loader) {
        INSTANCE = this;
        this.version = version;
        this.loader = loader;
    }

    public abstract MultiLoaderAPI getLoader();

    public enum GameVersion { V12, V16, V18, V19, V20, V21 }
    public enum ModLoader { FABRIC, FORGE, LEGACY, NEOFORGE }
}