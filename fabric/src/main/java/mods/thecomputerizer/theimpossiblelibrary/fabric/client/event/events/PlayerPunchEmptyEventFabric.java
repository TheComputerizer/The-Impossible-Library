package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayerPunchEmptyEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;

public class PlayerPunchEmptyEventFabric extends PlayerPunchEmptyEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(LeftClickEmpty::getItemStack);
    }
    
    @Override protected WorldAPI<?> getWorld() {
        return wrapWorld(LeftClickEmpty::getWorld);
    }

    @Override
    protected EventFieldWrapper<Object[],ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> EventHelper.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(EventHelper.setActionResult(result)),PASS);
    }

    @Override
    protected EventFieldWrapper<Object[],Facing> wrapFacingField() {
        return wrapGenericGetter(event -> EventHelper.getFacing(event.getFace()),Facing.UP);
    }

    @Override
    protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(LeftClickEmpty::getPlayer);
    }

    @Override
    protected EventFieldWrapper<Object[],BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(LeftClickEmpty::getPos);
    }
}