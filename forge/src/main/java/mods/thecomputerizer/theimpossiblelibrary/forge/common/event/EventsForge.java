package mods.thecomputerizer.theimpossiblelibrary.forge.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;
import java.util.Objects;

public class EventsForge {
    
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
    
    public static ActionResultType setActionResult(ActionResult result) {
        switch(result) {
            case CONSUME: return ActionResultType.CONSUME;
            case PASS: return ActionResultType.PASS;
            case SUCCESS: return ActionResultType.SUCCESS;
            default: return ActionResultType.FAIL;
        }
    }
}