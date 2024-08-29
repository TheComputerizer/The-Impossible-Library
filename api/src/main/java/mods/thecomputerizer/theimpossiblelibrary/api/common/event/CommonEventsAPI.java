package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import org.joml.Vector3d;

import javax.annotation.Nullable;

public interface CommonEventsAPI {

    void defineEvents();
    <A> Box getAABB(A aabb);
    <A> ActionResult getActionResult(A result);
    <E> Result getEventResult(E result);
    <D> @Nullable Facing getFacing(@Nullable D direction);
    <H> Hand getHand(H hand);
    <V> Vector3d getVec3d(V vector);
    boolean isDefined();
    void postCustomTick(CustomTick ticker);
    <E extends EventWrapper<?>> void register(E wrapper);
    <A> A setAABB(Box box);
    <A> A setActionResult(ActionResult result);
    <E> E setEventResult(Result result);
    <D> @Nullable D setFacing(@Nullable Facing facing);
    <H> H setHand(Hand hand);
    <V> V setVec3d(Vector3d vector);
}