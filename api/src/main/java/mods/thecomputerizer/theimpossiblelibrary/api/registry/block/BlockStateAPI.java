package mods.thecomputerizer.theimpossiblelibrary.api.registry.block;

public interface BlockStateAPI<B,S> {

    B getBlock();
    BlockAPI<B> getBlockAPI();
    S getState();
}