package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractEmptyEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.Events1_12_2;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_EMPTY;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractEventType.Hand.MAINHAND;

public class PlayerInteractEmptyEvent1_12_2 extends PlayerInteractEmptyEventWrapper<RightClickEmpty> {

    @SubscribeEvent
    public static void onEvent(RightClickEmpty event) {
        PLAYER_INTERACT_EMPTY.invoke(event);
    }

    @Override
    protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(RightClickEmpty::getItemStack);
    }

    @Override
    protected WorldAPI<?> getWorld() {
        return wrapWorld(RightClickEmpty::getWorld);
    }

    @Override
    protected EventFieldWrapper<RightClickEmpty,ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> Events1_12_2.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(Events1_12_2.setActionResult(result)),PASS);
    }

    @Override
    protected EventFieldWrapper<RightClickEmpty,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> Events1_12_2.getFacing(event.getFace()),Facing.UP);
    }

    @Override
    protected EventFieldWrapper<RightClickEmpty,Hand> wrapHandField() {
        return wrapGenericGetter(event -> Events1_12_2.getHand(event.getHand()),MAINHAND);
    }

    @Override
    protected EventFieldWrapper<RightClickEmpty,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(RightClickEmpty::getEntityPlayer);
    }

    @Override
    protected EventFieldWrapper<RightClickEmpty,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(RightClickEmpty::getPos);
    }
}