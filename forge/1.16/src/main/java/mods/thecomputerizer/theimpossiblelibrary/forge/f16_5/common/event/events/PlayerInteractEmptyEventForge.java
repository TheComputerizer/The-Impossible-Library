package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractEmptyEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.EventsForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractEventType.Hand.MAINHAND;

public class PlayerInteractEmptyEventForge extends PlayerInteractEmptyEventWrapper<RightClickEmpty> {

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
        return wrapGenericBoth(event -> EventsForge.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(EventsForge.setActionResult(result)),PASS);
    }

    @Override
    protected EventFieldWrapper<RightClickEmpty,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> EventsForge.getFacing(event.getFace()),Facing.UP);
    }

    @Override
    protected EventFieldWrapper<RightClickEmpty,Hand> wrapHandField() {
        return wrapGenericGetter(event -> EventsForge.getHand(event.getHand()),MAINHAND);
    }

    @Override
    protected EventFieldWrapper<RightClickEmpty,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(RightClickEmpty::getPlayer);
    }

    @Override
    protected EventFieldWrapper<RightClickEmpty,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(RightClickEmpty::getPos);
    }
}