package mods.thecomputerizer.theimpossiblelibrary.api.registry.block;

public interface BlockStateAPI<S> {

    BlockAPI<?> getBlockAPI();
    S getState();
}