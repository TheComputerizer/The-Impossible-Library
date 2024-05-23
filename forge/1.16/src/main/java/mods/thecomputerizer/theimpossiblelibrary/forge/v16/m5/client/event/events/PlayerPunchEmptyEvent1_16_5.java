package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayerPunchEmptyEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.Events1_16_5;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;

public class PlayerPunchEmptyEvent1_16_5 extends PlayerPunchEmptyEventWrapper<LeftClickEmpty> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LeftClickEmpty event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(LeftClickEmpty::getItemStack);
    }

    @Override
    protected WorldAPI<?> getWorld() {
        return wrapWorld(LeftClickEmpty::getWorld);
    }

    @Override
    protected EventFieldWrapper<LeftClickEmpty,ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> Events1_16_5.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(Events1_16_5.setActionResult(result)),PASS);
    }

    @Override
    protected EventFieldWrapper<LeftClickEmpty,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> Events1_16_5.getFacing(event.getFace()),Facing.UP);
    }

    @Override
    protected EventFieldWrapper<LeftClickEmpty,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(LeftClickEmpty::getPlayer);
    }

    @Override
    protected EventFieldWrapper<LeftClickEmpty,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(LeftClickEmpty::getPos);
    }
}