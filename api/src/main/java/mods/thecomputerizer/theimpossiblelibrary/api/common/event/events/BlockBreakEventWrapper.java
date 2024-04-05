package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;

import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_BREAK;

@Getter
public abstract class BlockBreakEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerAPI<?> player;
    protected BlockStateAPI<?> state;
    protected WorldAPI<?> world;
    protected int xp;

    protected BlockBreakEventWrapper() {
        super(BLOCK_BREAK);
    }

    protected abstract Function<E,?> getPlayerFunc();
    protected abstract Function<E,?> getStateFunc();
    protected abstract Function<E,?> getWorldFunc();

    @Override
    protected void populate() {
        this.player = WrapperHelper.wrapPlayer(getPlayerFunc().apply(this.event));
        this.state = WrapperHelper.wrapState(getStateFunc().apply(this.event));
        this.world = WrapperHelper.wrapWorld(getWorldFunc().apply(this.event));
    }

    public abstract void setXP(int xp);
}