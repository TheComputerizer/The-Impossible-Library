package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.Events1_16_5;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;

public class PlayerInteractItemEvent1_16_5 extends PlayerInteractItemEventWrapper<RightClickItem> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(RightClickItem event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(RightClickItem::getItemStack);
    }

    @Override
    protected WorldAPI<?> getWorld() {
        return wrapWorld(RightClickItem::getWorld);
    }

    @Override
    protected EventFieldWrapper<RightClickItem,ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> Events1_16_5.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(Events1_16_5.setActionResult(result)),PASS);
    }

    @Override
    protected EventFieldWrapper<RightClickItem,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> Events1_16_5.getFacing(event.getFace()),Facing.UP);
    }

    @Override
    protected EventFieldWrapper<RightClickItem,Hand> wrapHandField() {
        return wrapGenericGetter(event -> Events1_16_5.getHand(event.getHand()),MAINHAND);
    }

    @Override
    protected EventFieldWrapper<RightClickItem,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(RightClickItem::getPlayer);
    }

    @Override
    protected EventFieldWrapper<RightClickItem,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(RightClickItem::getPos);
    }
}