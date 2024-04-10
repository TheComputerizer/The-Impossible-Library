package mods.thecomputerizer.theimpossiblelibrary.api.block;

public interface BlockStateAPI<S> {

    BlockAPI<?> getBlockAPI();
    S getState();
}