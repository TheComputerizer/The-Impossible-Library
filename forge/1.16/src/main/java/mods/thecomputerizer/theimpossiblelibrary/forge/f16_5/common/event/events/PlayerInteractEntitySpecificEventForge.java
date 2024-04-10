package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractEntitySpecificEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.EventsForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractEventType.Hand.MAINHAND;

public class PlayerInteractEntitySpecificEventForge extends PlayerInteractEntitySpecificEventWrapper<EntityInteractSpecific> {

    @Override
    protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(EntityInteractSpecific::getItemStack);
    }

    @Override
    protected WorldAPI<?> getWorld() {
        return wrapWorld(EntityInteractSpecific::getWorld);
    }

    @Override
    protected EventFieldWrapper<EntityInteractSpecific,ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> EventsForge.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(EventsForge.setActionResult(result)),PASS);
    }

    @Override
    protected EventFieldWrapper<EntityInteractSpecific,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> EventsForge.getFacing(event.getFace()),Facing.UP);
    }

    @Override
    protected EventFieldWrapper<EntityInteractSpecific,Hand> wrapHandField() {
        return wrapGenericGetter(event -> EventsForge.getHand(event.getHand()),MAINHAND);
    }

    @Override
    protected EventFieldWrapper<EntityInteractSpecific,Vector3d> wrapLocalPosField() {
        return wrapGenericGetter(event -> EventsForge.getVec3d(event.getLocalPos()),VectorHelper.ZERO_3D);
    }

    @Override
    protected EventFieldWrapper<EntityInteractSpecific,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(EntityInteractSpecific::getPlayer);
    }

    @Override
    protected EventFieldWrapper<EntityInteractSpecific,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(EntityInteractSpecific::getPos);
    }

    @Override
    protected EventFieldWrapper<EntityInteractSpecific,EntityAPI<?>> wrapTargetField() {
        return wrapEntityGetter(EntityInteractSpecific::getTarget);
    }
}