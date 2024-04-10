package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayerPunchEmptyEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.EventsLegacy;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.PLAYER_PUNCH_EMPTY;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.ActionResult.PASS;

public class PlayerPunchEmptyEventLegacy extends PlayerPunchEmptyEventWrapper<LeftClickEmpty> {

    @SubscribeEvent
    public static void onEvent(LeftClickEmpty event) {
        PLAYER_PUNCH_EMPTY.invoke(event);
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
        return wrapGenericBoth(event -> EventsLegacy.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(EventsLegacy.setActionResult(result)),PASS);
    }

    @Override
    protected EventFieldWrapper<LeftClickEmpty,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> EventsLegacy.getFacing(event.getFace()),Facing.UP);
    }

    @Override
    protected EventFieldWrapper<LeftClickEmpty,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(LeftClickEmpty::getEntityPlayer);
    }

    @Override
    protected EventFieldWrapper<LeftClickEmpty,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(LeftClickEmpty::getPos);
    }
}