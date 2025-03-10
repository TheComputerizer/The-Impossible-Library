package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractEntityEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.UP;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;

public abstract class PlayerInteractEntityEventForge extends PlayerInteractEntityEventWrapper<EntityInteract> {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(EntityInteract::getItemStack);
    }
    
    @Override public void setEvent(EntityInteract event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<EntityInteract,ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> EventHelper.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(EventHelper.setActionResult(result)),PASS);
    }

    @Override protected EventFieldWrapper<EntityInteract,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> EventHelper.getFacing(event.getFace()),UP);
    }

    @Override protected EventFieldWrapper<EntityInteract,Hand> wrapHandField() {
        return wrapGenericGetter(event -> EventHelper.getHand(event.getHand()),MAINHAND);
    }

    @Override protected EventFieldWrapper<EntityInteract,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(EntityInteract::getEntity);
    }

    @Override protected EventFieldWrapper<EntityInteract,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(EntityInteract::getPos);
    }

    @Override protected EventFieldWrapper<EntityInteract,EntityAPI<?,?>> wrapTargetField() {
        return wrapEntityGetter(EntityInteract::getTarget);
    }
}