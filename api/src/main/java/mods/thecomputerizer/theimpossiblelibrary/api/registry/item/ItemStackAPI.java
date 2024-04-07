package mods.thecomputerizer.theimpossiblelibrary.api.registry.item;

public interface ItemStackAPI<S> {

    ItemAPI<?> getItemAPI();
    S getStack();
}