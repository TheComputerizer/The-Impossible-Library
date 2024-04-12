package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import lombok.Getter;

@Getter
public abstract class BlockStateAPI<S> {

    protected final S state;

    protected BlockStateAPI(S state) {
        this.state = state;
    }

    public abstract BlockAPI<?> getBlock();
    public abstract MaterialAPI<?> getMaterial();
}