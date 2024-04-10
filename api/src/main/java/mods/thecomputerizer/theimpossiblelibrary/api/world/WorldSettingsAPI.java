package mods.thecomputerizer.theimpossiblelibrary.api.world;

public interface WorldSettingsAPI<S> {

    long getSeed();
    S getWorldSettings();
    boolean isHardcore();
}