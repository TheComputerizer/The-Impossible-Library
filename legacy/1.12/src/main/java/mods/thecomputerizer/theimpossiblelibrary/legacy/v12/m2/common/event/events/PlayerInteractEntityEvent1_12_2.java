package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractEntityEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.Events1_12_2;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_ENTITY;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;

public class PlayerInteractEntityEvent1_12_2 extends PlayerInteractEntityEventWrapper<EntityInteract> {

    @SubscribeEvent
    public static void onEvent(EntityInteract event) {
        PLAYER_INTERACT_ENTITY.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(EntityInteract event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override
    protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(EntityInteract::getItemStack);
    }

    @Override
    protected WorldAPI<?> getWorld() {
        return wrapWorld(EntityInteract::getWorld);
    }

    @Override
    protected EventFieldWrapper<EntityInteract,ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> Events1_12_2.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(Events1_12_2.setActionResult(result)),PASS);
    }

    @Override
    protected EventFieldWrapper<EntityInteract,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> Events1_12_2.getFacing(event.getFace()),Facing.UP);
    }

    @Override
    protected EventFieldWrapper<EntityInteract,Hand> wrapHandField() {
        return wrapGenericGetter(event -> Events1_12_2.getHand(event.getHand()),MAINHAND);
    }

    @Override
    protected EventFieldWrapper<EntityInteract,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(EntityInteract::getEntityPlayer);
    }

    @Override
    protected EventFieldWrapper<EntityInteract,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(EntityInteract::getPos);
    }

    @Override
    protected EventFieldWrapper<EntityInteract,EntityAPI<?,?>> wrapTargetField() {
        return wrapEntityGetter(EntityInteract::getTarget);
    }
}