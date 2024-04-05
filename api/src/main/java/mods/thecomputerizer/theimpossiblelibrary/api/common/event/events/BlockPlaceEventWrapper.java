package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;

import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_PLACE;

@Getter
public abstract class BlockPlaceEventWrapper<E> extends CommonEventWrapper<E> {

    protected EntityAPI<?> entity;
    protected BlockStateAPI<?> placed;
    protected BlockStateAPI<?> placedAgainst;
    protected WorldAPI<?> world;

    protected BlockPlaceEventWrapper() {
        super(BLOCK_PLACE);
    }

    protected abstract Function<E,?> getEntityFunc();
    protected abstract Function<E,?> getPlacedFunc();
    protected abstract Function<E,?> getPlacedAgainstFunc();
    protected abstract Function<E,?> getWorldFunc();

    @Override
    protected void populate() {
        this.entity = WrapperHelper.wrapEntity(getEntityFunc().apply(this.event));
        this.placed = WrapperHelper.wrapState(getPlacedFunc().apply(this.event));
        this.placedAgainst = WrapperHelper.wrapState(getPlacedAgainstFunc().apply(this.event));
        this.world = WrapperHelper.wrapWorld(getWorldFunc().apply(this.event));
    }
}