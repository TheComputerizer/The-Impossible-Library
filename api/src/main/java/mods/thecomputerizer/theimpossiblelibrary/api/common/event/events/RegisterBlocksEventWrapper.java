package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonRegistryEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_BLOCKS;

public abstract class RegisterBlocksEventWrapper<E> extends CommonRegistryEventType<E,BlockAPI<?>> {

    protected RegisterBlocksEventWrapper() {
        super(REGISTER_BLOCKS);
    }
}
