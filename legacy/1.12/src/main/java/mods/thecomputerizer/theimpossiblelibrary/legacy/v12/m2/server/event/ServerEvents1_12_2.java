package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server.event.events.ServerTickEvent1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.util.CustomTick1_12_2;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.FAIL;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.SUCCESS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.OFFHAND;
import static mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventWrapper.ServerType.TICK_SERVER;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public class ServerEvents1_12_2 implements ServerEventsAPI {

    private boolean defined;

    public ServerEvents1_12_2() {
        this.defined = false;
    }

    @Override
    public void defineEvents() {
        TICK_SERVER.setConnector(new ServerTickEvent1_12_2());
        this.defined = true;
    }
    
    @Override public <A> Box getAABB(A aabb) {
        AxisAlignedBB box = (AxisAlignedBB)aabb;
        return ShapeHelper.box(box.minX,box.minY,box.minZ,box.maxX,box.maxY,box.maxZ);
    }
    
    @Override public <A> ActionResult getActionResult(A result) {
        return result==EnumActionResult.PASS ? PASS : (result==EnumActionResult.FAIL ? FAIL : SUCCESS);
    }
    
    @Override public <E> Result getEventResult(E result) {
        return result==Event.Result.DEFAULT ? DEFAULT : (result==Event.Result.DENY ? DENY : ALLOW);
    }
    
    @Override public <D> Facing getFacing(D direction) {
        if(Objects.isNull(direction)) return null;
        switch((EnumFacing)direction) {
            case DOWN: return Facing.DOWN;
            case EAST: return Facing.EAST;
            case NORTH: return Facing.NORTH;
            case SOUTH: return Facing.SOUTH;
            case UP: return Facing.UP;
            default: return Facing.WEST;
        }
    }
    
    @Override public <H> Hand getHand(H hand) {
        return hand==EnumHand.MAIN_HAND ? MAINHAND : OFFHAND;
    }
    
    @Override public <V> Vector3d getVec3d(V vector) {
        Vec3d vec = (Vec3d)vector;
        return new Vector3d(vec.x,vec.y,vec.z);
    }
    
    @Override public boolean isDefined() {
        return this.defined;
    }

    @Override public void postCustomTick(CustomTick ticker) {
        EVENT_BUS.post(new CustomTick1_12_2(ticker));
    }

    @Override public <E extends EventWrapper<?>> void register(E wrapper) {
        EVENT_BUS.register(wrapper.getClass());
    }
    
    @SuppressWarnings("unchecked")
    @Override public AxisAlignedBB setAABB(Box box) {
        return new AxisAlignedBB(box.min.x,box.min.y,box.min.z,box.max.x,box.max.y,box.max.z);
    }
    
    @SuppressWarnings("unchecked")
    @Override public EnumActionResult setActionResult(ActionResult result) {
        return result==PASS ? EnumActionResult.PASS : (result==FAIL ? EnumActionResult.FAIL : EnumActionResult.SUCCESS);
    }
    
    @SuppressWarnings("unchecked")
    @Override public Event.Result setEventResult(Result result) {
        return result==DEFAULT ? Event.Result.DEFAULT : (result==DENY ? Event.Result.DENY : Event.Result.ALLOW);
    }
    
    @SuppressWarnings("unchecked")
    @Override public @Nullable EnumFacing setFacing(@Nullable Facing facing) {
        if(Objects.isNull(facing)) return null;
        switch(facing) {
            case DOWN: return EnumFacing.DOWN;
            case EAST: return EnumFacing.EAST;
            case NORTH: return EnumFacing.NORTH;
            case SOUTH: return EnumFacing.SOUTH;
            case UP: return EnumFacing.UP;
            default: return EnumFacing.WEST;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override public EnumHand setHand(Hand hand) {
        return hand==MAINHAND ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
    }
    
    @SuppressWarnings("unchecked")
    @Override public Vec3d setVec3d(Vector3d vector) {
        return new Vec3d(vector.x,vector.y,vector.z);
    }
}