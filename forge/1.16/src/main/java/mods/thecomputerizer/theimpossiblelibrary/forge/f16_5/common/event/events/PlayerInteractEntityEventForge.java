package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractEntityEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.EventsForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractEventType.Hand.MAINHAND;

public class PlayerInteractEntityEventForge extends PlayerInteractEntityEventWrapper<EntityInteract> {

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
        return wrapGenericBoth(event -> EventsForge.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(EventsForge.setActionResult(result)),PASS);
    }

    @Override
    protected EventFieldWrapper<EntityInteract,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> EventsForge.getFacing(event.getFace()),Facing.UP);
    }

    @Override
    protected EventFieldWrapper<EntityInteract,Hand> wrapHandField() {
        return wrapGenericGetter(event -> EventsForge.getHand(event.getHand()),MAINHAND);
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