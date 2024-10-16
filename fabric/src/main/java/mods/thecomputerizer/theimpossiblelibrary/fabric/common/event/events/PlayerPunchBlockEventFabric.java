package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
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

import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.UP;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;
import static net.fabricmc.fabric.api.event.player.AttackBlockCallback.EVENT;

public class PlayerPunchBlockEventFabric extends PlayerPunchBlockEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return EVENT;
    }
    
    @Override protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(wrapArrayGetter(0));
    }
    
    @Override protected WorldAPI<?> getWorld() {
        return wrapWorld(wrapArrayGetter(1));
    }
    
    @Override protected EventFieldWrapper<Object[],Result> wrapBlockResultField() {
        return wrapGenericBoth(wrapArrayGetter(0), (args,result) -> {},DEFAULT);
    }
    
    @Override protected EventFieldWrapper<Object[],ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(wrapArrayGetter(0), (args,result) -> {},PASS);
    }
    
    @Override protected EventFieldWrapper<Object[],Facing> wrapFacingField() {
        return wrapGenericGetter(wrapArrayGetter(0),UP);
    }
    
    @Override protected EventFieldWrapper<Object[],Hand> wrapHandField() {
        return wrapGenericGetter(wrapArrayGetter(2),MAINHAND);
    }

    @Override protected EventFieldWrapper<Object[],Vector3d> wrapHitVecField() {
        return wrapGenericGetter(event -> VectorHelper.zero3D(),VectorHelper.zero3D());
    }

    @Override protected EventFieldWrapper<Object[],Result> wrapItemResultField() {
        return wrapGenericBoth(wrapArrayGetter(0),(args,result) -> {},DEFAULT);
    }

    @Override protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(wrapArrayGetter(0));
    }

    @Override protected EventFieldWrapper<Object[],BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(wrapArrayGetter(3));
    }
}