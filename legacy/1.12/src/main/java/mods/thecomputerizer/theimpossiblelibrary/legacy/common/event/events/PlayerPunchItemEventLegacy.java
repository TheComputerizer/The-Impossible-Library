package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPunchItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;

public class PlayerPunchItemEventLegacy extends PlayerPunchItemEventWrapper<Object> { //TODO

    @Override
    protected ItemStackAPI<?> getStackInHand() {
        return null;
    }

    @Override
    protected WorldAPI<?> getWorld() {
        return null;
    }

    @Override
    protected EventFieldWrapper<Object,ActionResult> wrapCancelResultField() {
        return null;
    }

    @Override
    protected EventFieldWrapper<Object,Facing> wrapFacingField() {
        return null;
    }

    @Override
    protected EventFieldWrapper<Object,Hand> wrapHandField() {
        return null;
    }

    @Override
    protected EventFieldWrapper<Object,PlayerAPI<?>> wrapPlayerField() {
        return null;
    }

    @Override
    protected EventFieldWrapper<Object,BlockPosAPI<?>> wrapPosField() {
        return null;
    }
}