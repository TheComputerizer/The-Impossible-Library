package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;

import javax.annotation.Nullable;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.OFFHAND;
import static net.minecraft.world.InteractionHand.MAIN_HAND;
import static net.minecraft.world.InteractionHand.OFF_HAND;

public abstract class CommonEvents1_18_2 implements CommonEventsAPI {
    
    private boolean defined;
    
    protected CommonEvents1_18_2() {
        this.defined = false;
    }
    
    @Override public void defineEvents() {
        this.defined = true;
    }
    
    @Override public <A> Box getAABB(A aabb) {
        AABB box = (AABB)aabb;
        return ShapeHelper.box(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }
    
    @Override public <A> ActionResult getActionResult(A result) {
        return switch((InteractionResult)result) {
            case CONSUME, CONSUME_PARTIAL -> ActionResult.CONSUME;
            case PASS -> ActionResult.PASS;
            case SUCCESS -> ActionResult.SUCCESS;
            case FAIL -> ActionResult.FAIL;
        };
    }
    
    @Override public @Nullable <D> Facing getFacing(@Nullable D direction) {
        if(Objects.isNull(direction)) return null;
        return switch((Direction)direction) {
            case DOWN -> Facing.DOWN;
            case EAST -> Facing.EAST;
            case NORTH -> Facing.NORTH;
            case SOUTH -> Facing.SOUTH;
            case UP -> Facing.UP;
            case WEST -> Facing.WEST;
        };
    }
    
    @Override public <H> Hand getHand(H hand) {
        return hand==MAIN_HAND ? MAINHAND : OFFHAND;
    }
    
    @Override public <V> Vector3d getVec3d(V vector) {
        Vec3 vec = (Vec3)vector;
        return new Vector3d(vec.x,vec.y,vec.z);
    }
    
    @Override public boolean isDefined() {
        return this.defined;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <B> B setAABB(Box box) {
        return (B)new AABB(box.min.x,box.min.y,box.min.z,box.max.x,box.max.y,box.max.z);
    }
    
    @SuppressWarnings("unchecked")
    @Override public <A> A setActionResult(ActionResult result) {
        return switch(result) {
            case CONSUME -> (A)InteractionResult.CONSUME;
            case PASS -> (A)InteractionResult.PASS;
            case SUCCESS -> (A)InteractionResult.SUCCESS;
            case FAIL -> (A)InteractionResult.FAIL;
        };
    }
    
    @SuppressWarnings("unchecked")
    @Override public <F> @Nullable F setFacing(@Nullable Facing facing) {
        if(Objects.isNull(facing)) return null;
        return switch(facing) {
            case DOWN -> (F)Direction.DOWN;
            case EAST -> (F)Direction.EAST;
            case NORTH -> (F)Direction.NORTH;
            case SOUTH -> (F)Direction.SOUTH;
            case UP -> (F)Direction.UP;
            case WEST -> (F)Direction.WEST;
        };
    }
    
    @SuppressWarnings("unchecked")
    @Override public <H> H setHand(Hand hand) {
        return (H)(hand==MAINHAND ? MAIN_HAND : OFF_HAND);
    }
    
    @SuppressWarnings("unchecked")
    @Override public <V> V setVec3d(Vector3d vector) {
        return (V)new Vec3(vector.x,vector.y,vector.z);
    }
}
