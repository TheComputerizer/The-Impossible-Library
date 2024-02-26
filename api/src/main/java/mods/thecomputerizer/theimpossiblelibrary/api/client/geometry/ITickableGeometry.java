package mods.thecomputerizer.theimpossiblelibrary.api.client.geometry;

public interface ITickableGeometry<T> {

    T setTime(int time);
    T init();
    boolean isInitialized();
    void onTick();
    T reset();
}