package mods.thecomputerizer.theimpossiblelibrary.api.world;

public interface WorldAPI<W> {

    WorldSettingsAPI<?> getSettings();
    W getWorld();
    boolean isClient();
    boolean isServer();
}