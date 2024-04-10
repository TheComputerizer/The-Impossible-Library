package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockInteractEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

public class BlockInteractEventLegacy extends BlockInteractEventWrapper<Object> { //TODO

    @Override
    protected EventFieldWrapper<Object,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> null);
    }

    @Override
    protected EventFieldWrapper<Object,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(event -> null);
    }

    @Override
    protected EventFieldWrapper<Object,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }

    @Override
    protected EventFieldWrapper<Object,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(event -> null);
    }
}