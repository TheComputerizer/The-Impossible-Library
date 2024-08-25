package mods.thecomputerizer.theimpossiblelibrary.forge.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraftforge.eventbus.api.Event;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.OFFHAND;

public class EventsForge {
    
    //TODO Even if these methods stay static they should be referencing the API heirarchy for better version control
    public static ActionResult getActionResult(ActionResultType result) {
        switch(result) {
            case CONSUME: return ActionResult.CONSUME;
            case PASS: return ActionResult.PASS;
            case SUCCESS: return ActionResult.SUCCESS;
            default: return ActionResult.FAIL;
        }
    }
    
    public static @Nullable Facing getFacing(@Nullable Direction facing) {
        if(Objects.isNull(facing)) return null;
        switch(facing) {
            case DOWN: return Facing.DOWN;
            case EAST: return Facing.EAST;
            case NORTH: return Facing.NORTH;
            case SOUTH: return Facing.SOUTH;
            case UP: return Facing.UP;
            default: return Facing.WEST;
        }
    }
    
    public static Hand getHand(net.minecraft.util.Hand hand) {
        return hand==net.minecraft.util.Hand.MAIN_HAND ? MAINHAND : OFFHAND;
    }
    
    public static Result getResult(Event.Result result) {
        return result==Event.Result.DEFAULT ? DEFAULT : (result==Event.Result.DENY ? DENY : ALLOW);
    }
    
    public static Vector3d getVec3d(net.minecraft.util.math.vector.Vector3d vec) {
        return new Vector3d(vec.x,vec.y,vec.z);
    }
    
    public static ActionResultType setActionResult(ActionResult result) {
        switch(result) {
            case CONSUME: return ActionResultType.CONSUME;
            case PASS: return ActionResultType.PASS;
            case SUCCESS: return ActionResultType.SUCCESS;
            default: return ActionResultType.FAIL;
        }
    }
    
    public static Event.Result setResult(Result result) {
        return result==DEFAULT ? Event.Result.DEFAULT : (result==DENY ? Event.Result.DENY : Event.Result.ALLOW);
    }
}