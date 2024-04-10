package mods.thecomputerizer.theimpossiblelibrary.api.spawn;

public interface SpawnHelperAPI<E> {

    SpawnEntryAPI<E> getEntry(Class<? extends E> clazz, int weight, int maxGroup, int minGroup);
}