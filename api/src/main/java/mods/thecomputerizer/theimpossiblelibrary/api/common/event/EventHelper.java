package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.EventType;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings("unused") public class EventHelper {

    public static <E extends EventWrapper<?>> void addListener(EventType<E> type, Consumer<E> invoker) {
        CommonEventsAPI api = getEventsAPI(type.isClient());
        if(!api.isDefined()) api.defineEvents();
        type.addInvoker(invoker);
    }
    
    public static <A> Box getAABB(A aabb) {
        return getAABB(false,aabb);
    }
    
    public static <A> Box getAABB(EventType<?> type, A aabb) {
        return getAABB(type.isClient(),aabb);
    }
    
    public static <A> Box getAABB(boolean client, A aabb) {
        return getEventsAPI(client).getAABB(aabb);
    }
    
    public static <A> ActionResult getActionResult(A result) {
        return getActionResult(false,result);
    }
    
    public static <A> ActionResult getActionResult(EventType<?> type, A result) {
        return getActionResult(type.isClient(),result);
    }
    
    public static <A> ActionResult getActionResult(boolean client, A result) {
        return getEventsAPI(client).getActionResult(result);
    }

    public static CommonEventsAPI getCommonEventsAPI() {
        return TILRef.getCommonSubAPI(CommonAPI::getCommonEvents);
    }
    
    public static <E> Result getEventResult(E result) {
        return getEventResult(false,result);
    }
    
    public static <E> Result getEventResult(EventType<?> type, E result) {
        return getEventResult(type.isClient(),result);
    }
    
    public static <E> Result getEventResult(boolean client, E result) {
        return getEventsAPI(client).getEventResult(result);
    }

    public static CommonEventsAPI getEventsAPI(boolean client) {
        return client ? ClientEventHelper.getEventsAPI() : getCommonEventsAPI();
    }
    
    public static <D> @Nullable Facing getFacing(@Nullable D direction) {
        return getFacing(false,direction);
    }
    
    public static <D> @Nullable Facing getFacing(EventType<?> type, @Nullable D direction) {
        return getFacing(type.isClient(),direction);
    }
    
    public static <D> @Nullable Facing getFacing(boolean client, @Nullable D direction) {
        return getEventsAPI(client).getFacing(direction);
    }
    
    public static <H> Hand getHand(H hand) {
        return getHand(false,hand);
    }
    
    public static <H> Hand getHand(EventType<?> type, H hand) {
        return getHand(type.isClient(),hand);
    }
    
    public static <H> Hand getHand(boolean client, H hand) {
        return getEventsAPI(client).getHand(hand);
    }
    
    public static <B> OverlayType getOverlayBlockType(B blockType) {
        return ((ClientEventsAPI)getEventsAPI(true)).getOverlayBlockType(blockType);
    }
    
    public static <E> OverlayType getOverlayElementType(E elementType) {
        return ((ClientEventsAPI)getEventsAPI(true)).getOverlayElementType(elementType);
    }
    
    public static <V> Vector3d getVec3d(V vector) {
        return getVec3d(false,vector);
    }
    
    public static <V> Vector3d getVec3d(EventType<?> type, V vector) {
        return getVec3d(type.isClient(),vector);
    }
    
    public static <V> Vector3d getVec3d(boolean client, V vector) {
        return getEventsAPI(client).getVec3d(vector);
    }
    
    public static RenderContext initRenderer(Consumer<RenderContext> setters) {
        return ((ClientEventsAPI)getEventsAPI(true)).initRenderer(setters);
    }

    public static void initTILListeners(boolean client, boolean test) {
        if(client) ClientEventHelper.initTILClientListeners(test);
    }

    public static <E extends EventWrapper<?>> void registerWrapperImpl(E wrapper) {
        CommonEventsAPI api = getEventsAPI(wrapper.isClient());
        if(Objects.nonNull(api)) api.register(wrapper);
    }
    
    public static <A> A setAABB(Box box) {
        return setAABB(false,box);
    }
    
    public static <A> A setAABB(EventType<?> type, Box box) {
        return setAABB(type.isClient(),box);
    }
    
    public static <A> A setAABB(boolean client, Box box) {
        return getEventsAPI(client).setAABB(box);
    }
    
    public static <A> A setActionResult(ActionResult result) {
        return setActionResult(false,result);
    }
    
    public static <A> A setActionResult(EventType<?> type, ActionResult result) {
        return setActionResult(type.isClient(),result);
    }
    
    public static <A> A setActionResult(boolean client, ActionResult result) {
        return getEventsAPI(client).setActionResult(result);
    }
    
    public static <E> E setEventResult(Result result) {
        return setEventResult(false,result);
    }
    
    public static <E> E setEventResult(EventType<?> type, Result result) {
        return setEventResult(type.isClient(),result);
    }
    
    public static <E> E setEventResult(boolean client, Result result) {
        return getEventsAPI(client).setEventResult(result);
    }
    
    public static <D> @Nullable D setFacing(@Nullable Facing facing) {
        return setFacing(false,facing);
    }
    
    public static <D> @Nullable D setFacing(EventType<?> type, @Nullable Facing facing) {
        return setFacing(type.isClient(),facing);
    }
    
    public static <D> @Nullable D setFacing(boolean client, @Nullable Facing facing) {
        return getEventsAPI(client).setFacing(facing);
    }
    
    public static <H> H setHand(Hand hand) {
        return setHand(false,hand);
    }
    
    public static <H> H setHand(EventType<?> type, Hand hand) {
        return setHand(type.isClient(),hand);
    }
    
    public static <H> H setHand(boolean client, Hand hand) {
        return getEventsAPI(client).setHand(hand);
    }
    
    public static <V> V setVec3d(Vector3d vector) {
        return setVec3d(false,vector);
    }
    
    public static <V> V setVec3d(EventType<?> type, Vector3d vector) {
        return setVec3d(type.isClient(),vector);
    }
    
    public static <V> V setVec3d(boolean client, Vector3d vector) {
        return getEventsAPI(client).setVec3d(vector);
    }
}