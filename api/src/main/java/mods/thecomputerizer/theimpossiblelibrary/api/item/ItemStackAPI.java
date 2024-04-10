package mods.thecomputerizer.theimpossiblelibrary.api.item;

public interface ItemStackAPI<S> {

    ItemAPI<?> getItemAPI();
    S getStack();
}