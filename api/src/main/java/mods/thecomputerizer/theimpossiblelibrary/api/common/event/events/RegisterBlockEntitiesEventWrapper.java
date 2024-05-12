package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonRegistryEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_BLOCK_ENTITIES;

public abstract class RegisterBlockEntitiesEventWrapper<E> extends CommonRegistryEventType<E,BlockEntityAPI<?,?>> {

    protected RegisterBlockEntitiesEventWrapper() {
        super(REGISTER_BLOCK_ENTITIES);
    }
}
