package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayerPunchEmptyEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.PLAYER_PUNCH_EMPTY;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.UP;

public class PlayerPunchEmptyEvent1_12_2 extends PlayerPunchEmptyEventWrapper<LeftClickEmpty> {

    @SubscribeEvent
    public static void onEvent(LeftClickEmpty event) {
        PLAYER_PUNCH_EMPTY.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }

    @Override protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(getter("getItemStack"));
    }

    @Override protected WorldAPI<?> getWorld() {
        return wrapWorld(getter("getWorld"));
    }
    
    @Override public void setEvent(LeftClickEmpty event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<LeftClickEmpty,ActionResult> wrapCancelResultField() {
        return wrapActionResultBoth("getCancellationResult","setCancellationResult");
    }

    @Override protected EventFieldWrapper<LeftClickEmpty,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> EventHelper.getFacing(event.getFace()),UP);
    }

    @Override protected EventFieldWrapper<LeftClickEmpty,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(LeftClickEmpty::getEntityPlayer);
    }

    @Override protected EventFieldWrapper<LeftClickEmpty,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(LeftClickEmpty::getPos);
    }
}