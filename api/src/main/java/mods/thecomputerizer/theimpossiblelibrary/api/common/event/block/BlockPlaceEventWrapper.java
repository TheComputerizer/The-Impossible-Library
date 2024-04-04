package mods.thecomputerizer.theimpossiblelibrary.api.common.event.block;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_PLACE;

@Getter
public abstract class BlockPlaceEventWrapper<E,B,S,EN,W> extends BlockEventWrapper<E,B> {

    protected EntityAPI<EN> entity;
    protected BlockStateAPI<B,S> placed;
    protected BlockStateAPI<B,S> placedAgainst;
    protected WorldAPI<W> world;

    protected BlockPlaceEventWrapper() {
        super(BLOCK_PLACE);
    }

    protected void setPlaced(BlockStateAPI<B,S> state) {
        this.placed = state;
        setBlock(state.getBlockAPI());
    }
}