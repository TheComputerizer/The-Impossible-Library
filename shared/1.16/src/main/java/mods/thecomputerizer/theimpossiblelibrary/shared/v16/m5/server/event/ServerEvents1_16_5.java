package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.OFFHAND;

public abstract class ServerEvents1_16_5 implements ServerEventsAPI {
    
    private boolean defined;
    
    protected ServerEvents1_16_5() {
        this.defined = false;
    }
    
    @Override public void defineEvents() {
        this.defined = true;
    }
    
    @Override public <A> Box getAABB(A aabb) {
        AxisAlignedBB box = (AxisAlignedBB)aabb;
        return ShapeHelper.box(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }
    
    @Override public <A> ActionResult getActionResult(A result) {
        switch((ActionResultType)result) {
            case CONSUME: return ActionResult.CONSUME;
            case PASS: return ActionResult.PASS;
            case SUCCESS: return ActionResult.SUCCESS;
            default: return ActionResult.FAIL;
        }
    }
    
    @Override public @Nullable <D> Facing getFacing(@Nullable D direction) {
        if(Objects.isNull(direction)) return null;
        switch((Direction)direction) {
            case DOWN: return Facing.DOWN;
            case EAST: return Facing.EAST;
            case NORTH: return Facing.NORTH;
            case SOUTH: return Facing.SOUTH;
            case UP: return Facing.UP;
            default: return Facing.WEST;
        }
    }
    
    @Override public <H> Hand getHand(H hand) {
        return hand==net.minecraft.util.Hand.MAIN_HAND ? MAINHAND : OFFHAND;
    }
    
    @Override public <V> Vector3d getVec3d(V vector) {
        net.minecraft.util.math.vector.Vector3d vec = (net.minecraft.util.math.vector.Vector3d)vector;
        return new Vector3d(vec.x,vec.y,vec.z);
    }
    
    @Override public boolean isDefined() {
        return this.defined;
    }
    
    @SuppressWarnings("unchecked")
    @Override public AxisAlignedBB setAABB(Box box) {
        return new AxisAlignedBB(box.min.x,box.min.y,box.min.z,box.max.x,box.max.y,box.max.z);
    }
    
    @SuppressWarnings("unchecked")
    @Override public ActionResultType setActionResult(ActionResult result) {
        switch(result) {
            case CONSUME: return ActionResultType.CONSUME;
            case PASS: return ActionResultType.PASS;
            case SUCCESS: return ActionResultType.SUCCESS;
            default: return ActionResultType.FAIL;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override public @Nullable Direction setFacing(@Nullable Facing facing) {
        if(Objects.isNull(facing)) return null;
        switch(facing) {
            case DOWN: return Direction.DOWN;
            case EAST: return Direction.EAST;
            case NORTH: return Direction.NORTH;
            case SOUTH: return Direction.SOUTH;
            case UP: return Direction.UP;
            default: return Direction.WEST;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override public net.minecraft.util.Hand setHand(Hand hand) {
        return hand==MAINHAND ? net.minecraft.util.Hand.MAIN_HAND : net.minecraft.util.Hand.OFF_HAND;
    }
    
    @SuppressWarnings("unchecked")
    @Override public net.minecraft.util.math.vector.Vector3d setVec3d(Vector3d vector) {
        return new net.minecraft.util.math.vector.Vector3d(vector.x,vector.y,vector.z);
    }
}
