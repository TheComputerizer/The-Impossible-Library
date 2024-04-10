package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayerPunchEmptyEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.EventsForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.ActionResult.PASS;

public class PlayerPunchEmptyEventForge extends PlayerPunchEmptyEventWrapper<LeftClickEmpty> {

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
        return wrapGenericBoth(event -> EventsForge.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(EventsForge.setActionResult(result)),PASS);
    }

    @Override
    protected EventFieldWrapper<LeftClickEmpty,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> EventsForge.getFacing(event.getFace()),Facing.UP);
    }

    @Override
    protected EventFieldWrapper<LeftClickEmpty,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(LeftClickEmpty::getPlayer);
    }

    @Override
    protected EventFieldWrapper<LeftClickEmpty,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(LeftClickEmpty::getPos);
    }
}