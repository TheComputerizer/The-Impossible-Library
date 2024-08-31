package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractEntitySpecificEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.UP;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;

public class PlayerInteractEntitySpecificEventFabric extends PlayerInteractEntitySpecificEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(EntityInteractSpecific::getItemStack);
    }

    @Override protected WorldAPI<?> getWorld() {
        return wrapWorld(EntityInteractSpecific::getWorld);
    }

    @Override protected EventFieldWrapper<Object[],ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> EventHelper.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(EventHelper.setActionResult(result)),PASS);
    }

    @Override protected EventFieldWrapper<Object[],Facing> wrapFacingField() {
        return wrapGenericGetter(event -> EventHelper.getFacing(event.getFace()),UP);
    }

    @Override protected EventFieldWrapper<Object[],Hand> wrapHandField() {
        return wrapGenericGetter(event -> EventHelper.getHand(event.getHand()),MAINHAND);
    }

    @Override protected EventFieldWrapper<Object[],Vector3d> wrapLocalPosField() {
        return wrapGenericGetter(event -> EventHelper.getVec3d(event.getLocalPos()),VectorHelper.zero3D());
    }

    @Override protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(EntityInteractSpecific::getPlayer);
    }

    @Override protected EventFieldWrapper<Object[],BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(EntityInteractSpecific::getPos);
    }

    @Override protected EventFieldWrapper<Object[],EntityAPI<?,?>> wrapTargetField() {
        return wrapEntityGetter(EntityInteractSpecific::getTarget);
    }
}