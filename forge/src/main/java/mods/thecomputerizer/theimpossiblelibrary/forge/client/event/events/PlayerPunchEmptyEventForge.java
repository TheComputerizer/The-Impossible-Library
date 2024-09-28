package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayerPunchEmptyEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.PLAYER_PUNCH_EMPTY;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;

public class PlayerPunchEmptyEventForge extends PlayerPunchEmptyEventWrapper<LeftClickEmpty> {
    
    @SubscribeEvent
    public static void onEvent(LeftClickEmpty event) {
        PLAYER_PUNCH_EMPTY.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(LeftClickEmpty::getItemStack);
    }
    
    @Override protected WorldAPI<?> getWorld() {
        return wrapWorld(LeftClickEmpty::getWorld);
    }
    
    @Override public void setEvent(LeftClickEmpty event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<LeftClickEmpty,ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> EventHelper.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(EventHelper.setActionResult(result)),PASS);
    }

    @Override protected EventFieldWrapper<LeftClickEmpty,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> EventHelper.getFacing(event.getFace()),Facing.UP);
    }

    @Override protected EventFieldWrapper<LeftClickEmpty,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(LeftClickEmpty::getPlayer);
    }

    @Override protected EventFieldWrapper<LeftClickEmpty,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(LeftClickEmpty::getPos);
    }
}