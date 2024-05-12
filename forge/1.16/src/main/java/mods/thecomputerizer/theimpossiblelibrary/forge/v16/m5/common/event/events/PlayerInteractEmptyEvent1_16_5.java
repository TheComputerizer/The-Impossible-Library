package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractEmptyEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.Events1_16_5;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;

public class PlayerInteractEmptyEvent1_16_5 extends PlayerInteractEmptyEventWrapper<RightClickEmpty> {

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
        return wrapGenericBoth(event -> Events1_16_5.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(Events1_16_5.setActionResult(result)),PASS);
    }

    @Override
    protected EventFieldWrapper<RightClickEmpty,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> Events1_16_5.getFacing(event.getFace()),Facing.UP);
    }

    @Override
    protected EventFieldWrapper<RightClickEmpty,Hand> wrapHandField() {
        return wrapGenericGetter(event -> Events1_16_5.getHand(event.getHand()),MAINHAND);
    }

    @Override
    protected EventFieldWrapper<RightClickEmpty,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(RightClickEmpty::getPlayer);
    }

    @Override
    protected EventFieldWrapper<RightClickEmpty,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(RightClickEmpty::getPos);
    }
}