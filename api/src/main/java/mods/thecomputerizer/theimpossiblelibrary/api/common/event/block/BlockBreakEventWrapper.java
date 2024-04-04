package mods.thecomputerizer.theimpossiblelibrary.api.common.event.block;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_BREAK;

@Getter
public abstract class BlockBreakEventWrapper<E,B,S,P,W> extends BlockEventWrapper<E,B> {

    protected PlayerAPI<P> player;
    protected BlockStateAPI<B,S> state;
    protected WorldAPI<W> world;
    protected int xp;

    protected BlockBreakEventWrapper() {
        super(BLOCK_BREAK);
    }

    protected void setState(BlockStateAPI<B,S> state) {
        this.state = state;
        setBlock(state.getBlockAPI());
    }

    public abstract void setXP(int xp);
}