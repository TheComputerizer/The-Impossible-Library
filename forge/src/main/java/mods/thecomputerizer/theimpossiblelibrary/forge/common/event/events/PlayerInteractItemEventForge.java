package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.CommonForgeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.UP;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_ITEM;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;

public class PlayerInteractItemEventForge extends PlayerInteractItemEventWrapper<RightClickItem>
        implements CommonForgeEvent {
    
    @SubscribeEvent
    public static void onEvent(RightClickItem event) {
        PLAYER_INTERACT_ITEM.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(getter(STACK_GETTER));
    }
    
    @Override protected WorldAPI<?> getWorld() {
        return wrapWorld(getter(WORLD_GETTER));
    }
    
    @Override public void setEvent(RightClickItem event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<RightClickItem,ActionResult> wrapCancelResultField() {
        return wrapActionResultBoth("getCancellationResult","setCancellationResult");
    }

    @Override protected EventFieldWrapper<RightClickItem,Facing> wrapFacingField() {
        return wrapGenericGetter(getter("getFace",EventHelper::getFacing),UP);
    }

    @Override protected EventFieldWrapper<RightClickItem,Hand> wrapHandField() {
        return wrapGenericGetter(getter("getHand",EventHelper::getHand),MAINHAND);
    }

    @Override protected EventFieldWrapper<RightClickItem,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(getter(ENTITY_GETTER));
    }

    @Override protected EventFieldWrapper<RightClickItem,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(getter("getPos"));
    }
}