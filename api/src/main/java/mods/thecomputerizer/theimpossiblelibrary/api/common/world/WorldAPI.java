package mods.thecomputerizer.theimpossiblelibrary.api.common.world;

public interface WorldAPI<W> {

    W getWorld();
    boolean isClient();
    boolean isServer();
}