package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPunchEntityEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

public class PlayerPunchEntityEventForge extends PlayerPunchEntityEventWrapper<Object> {
    
    //public static void onEvent(Object event) {
    //    PLAYER_PUNCH_ENTITY.invoke(event);
    //} //TODO

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

    @Override protected EventFieldWrapper<Object,EntityAPI<?,?>> wrapTargetField() {
        return wrapEntityGetter(event -> null);
    }
}