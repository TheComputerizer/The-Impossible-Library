package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPunchBlockEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;

public class PlayerPunchBlockEventFabric extends PlayerPunchBlockEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(LeftClickBlock::getItemStack);
    }

    @Override protected WorldAPI<?> getWorld() {
        return wrapWorld(LeftClickBlock::getWorld);
    }

    @Override protected EventFieldWrapper<Object[],Result> wrapBlockResultField() {
        return wrapGenericBoth(event -> EventHelper.getEventResult(event.getUseBlock()),
                (event,result) -> event.setUseBlock(EventHelper.setEventResult(result)),DEFAULT);
    }

    @Override protected EventFieldWrapper<Object[],ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> EventHelper.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(EventHelper.setActionResult(result)),PASS);
    }

    @Override protected EventFieldWrapper<Object[],Facing> wrapFacingField() {
        return wrapGenericGetter(event -> EventHelper.getFacing(event.getFace()),Facing.UP);
    }

    @Override protected EventFieldWrapper<Object[],Hand> wrapHandField() {
        return wrapGenericGetter(event -> EventHelper.getHand(event.getHand()),MAINHAND);
    }

    @Override protected EventFieldWrapper<Object[],Vector3d> wrapHitVecField() {
        return wrapGenericGetter(event -> VectorHelper.zero3D(),VectorHelper.zero3D());
    }

    @Override protected EventFieldWrapper<Object[],Result> wrapItemResultField() {
        return wrapGenericBoth(event -> EventHelper.getEventResult(event.getUseItem()),
                (event,result) -> event.setUseItem(EventHelper.setEventResult(result)),DEFAULT);
    }

    @Override protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(LeftClickBlock::getPlayer);
    }

    @Override protected EventFieldWrapper<Object[],BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(LeftClickBlock::getPos);
    }
}