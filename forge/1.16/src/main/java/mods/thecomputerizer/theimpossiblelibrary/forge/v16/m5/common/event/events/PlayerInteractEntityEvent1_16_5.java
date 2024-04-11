package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractEntityEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.Events1_16_5;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractEventType.Hand.MAINHAND;

public class PlayerInteractEntityEvent1_16_5 extends PlayerInteractEntityEventWrapper<EntityInteract> {

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
        return wrapGenericBoth(event -> Events1_16_5.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(Events1_16_5.setActionResult(result)),PASS);
    }

    @Override
    protected EventFieldWrapper<EntityInteract,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> Events1_16_5.getFacing(event.getFace()),Facing.UP);
    }

    @Override
    protected EventFieldWrapper<EntityInteract,Hand> wrapHandField() {
        return wrapGenericGetter(event -> Events1_16_5.getHand(event.getHand()),MAINHAND);
    }

    @Override
    protected EventFieldWrapper<EntityInteract,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(EntityInteract::getPlayer);
    }

    @Override
    protected EventFieldWrapper<EntityInteract,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(EntityInteract::getPos);
    }

    @Override
    protected EventFieldWrapper<EntityInteract,EntityAPI<?>> wrapTargetField() {
        return wrapEntityGetter(EntityInteract::getTarget);
    }
}