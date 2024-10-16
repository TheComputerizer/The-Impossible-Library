package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPunchItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;

public class PlayerPunchItemEvent1_12_2 extends PlayerPunchItemEventWrapper<Object> { //TODO

    @Override protected ItemStackAPI<?> getStackInHand() {
        return null;
    }

    @Override protected WorldAPI<?> getWorld() {
        return null;
    }

    @Override protected EventFieldWrapper<Object,ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> null,(event,result) -> {},null);
    }

    @Override protected EventFieldWrapper<Object,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> null,null);
    }

    @Override protected EventFieldWrapper<Object,Hand> wrapHandField() {
        return wrapGenericGetter(event -> null,null);
    }

    @Override protected EventFieldWrapper<Object,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> null);
    }

    @Override protected EventFieldWrapper<Object,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(event -> null);
    }
}