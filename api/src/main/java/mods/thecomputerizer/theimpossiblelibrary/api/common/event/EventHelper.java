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
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings("unused") public class EventHelper {

    public static <E extends EventWrapper<?>> void addListener(EventType<E> type, Consumer<E> invoker) {
        CommonEventsAPI api = getEventsAPI(type.isClient(),type.isServer());
        if(!api.isDefined()) api.defineEvents();
        type.addInvoker(invoker);
    }
    
    public static <A> Box getAABB(A aabb) {
        return getAABB(false,false,aabb);
    }
    
    public static <A> Box getAABB(EventType<?> type, A aabb) {
        return getAABB(type.isClient(),type.isServer(),aabb);
    }
    
    public static <A> Box getAABB(boolean client, A aabb) {
        return getAABB(client,false,aabb);
    }
    
    public static <A> Box getAABB(boolean client, boolean server, A aabb) {
        return getEventsAPI(client,server).getAABB(aabb);
    }
    
    public static <A> ActionResult getActionResult(A result) {
        return getActionResult(false,false,result);
    }
    
    public static <A> ActionResult getActionResult(EventType<?> type, A result) {
        return getActionResult(type.isClient(),type.isServer(),result);
    }
    
    public static <A> ActionResult getActionResult(boolean client, A result) {
        return getActionResult(client,false,result);
    }
    
    public static <A> ActionResult getActionResult(boolean client, boolean server, A result) {
        return getEventsAPI(client,server).getActionResult(result);
    }

    public static CommonEventsAPI getCommonEventsAPI() {
        return TILRef.getCommonSubAPI(CommonAPI::getCommonEvents);
    }
    
    public static <E> Result getEventResult(E result) {
        return getEventResult(false,false,result);
    }
    
    public static <E> Result getEventResult(EventType<?> type, E result) {
        return getEventResult(type.isClient(),type.isServer(),result);
    }
    
    public static <E> Result getEventResult(boolean client, E result) {
        return getEventsAPI(client,false).getEventResult(result);
    }
    
    public static <E> Result getEventResult(boolean client, boolean server, E result) {
        return getEventsAPI(client,server).getEventResult(result);
    }

    public static CommonEventsAPI getEventsAPI(boolean client, boolean server) {
        return client ? ClientEventHelper.getEventsAPI() :
                (server ? ServerEventHelper.getEventsAPI() : getCommonEventsAPI());
    }
    
    public static <D> @Nullable Facing getFacing(@Nullable D direction) {
        return getFacing(false,false,direction);
    }
    
    public static <D> @Nullable Facing getFacing(EventType<?> type, @Nullable D direction) {
        return getFacing(type.isClient(),type.isServer(),direction);
    }
    
    public static <D> @Nullable Facing getFacing(boolean client, @Nullable D direction) {
        return getFacing(client,false,direction);
    }
    
    public static <D> @Nullable Facing getFacing(boolean client, boolean server, @Nullable D direction) {
        return getEventsAPI(client,server).getFacing(direction);
    }
    
    public static <H> Hand getHand(H hand) {
        return getHand(false,false,hand);
    }
    
    public static <H> Hand getHand(EventType<?> type, H hand) {
        return getHand(type.isClient(),type.isServer(),hand);
    }
    
    public static <H> Hand getHand(boolean client, H hand) {
        return getHand(client,false,hand);
    }
    
    public static <H> Hand getHand(boolean client, boolean server, H hand) {
        return getEventsAPI(client,server).getHand(hand);
    }
    
    public static <B> OverlayType getOverlayBlockType(B blockType) {
        return ((ClientEventsAPI)getEventsAPI(true,false)).getOverlayBlockType(blockType);
    }
    
    public static <E> OverlayType getOverlayElementType(E elementType) {
        return ((ClientEventsAPI)getEventsAPI(true,false)).getOverlayElementType(elementType);
    }
    
    public static CommonEventsAPI getServerEventsAPI() {
        return TILRef.getCommonSubAPI(CommonAPI::getCommonEvents);
    }
    
    public static <V> Vector3 getVec3d(V vector) {
        return getVec3d(false,false,vector);
    }
    
    public static <V> Vector3 getVec3d(EventType<?> type, V vector) {
        return getVec3d(type.isClient(),type.isServer(),vector);
    }
    
    public static <V> Vector3 getVec3d(boolean client, V vector) {
        return getVec3d(client,false,vector);
    }
    
    public static <V> Vector3 getVec3d(boolean client, boolean server, V vector) {
        return getEventsAPI(client,server).getVec3d(vector);
    }
    
    public static RenderContext initRenderer(Consumer<RenderContext> setters) {
        return ((ClientEventsAPI)getEventsAPI(true,false)).initRenderer(setters);
    }
    
    private static void initTILCommonListeners(boolean test) {
    
    }

    public static void initTILListeners(boolean client, boolean common, boolean server, boolean test) {
        if(client) ClientEventHelper.initTILClientListeners(test);
        if(common) initTILCommonListeners(test);
        if(server) ServerEventHelper.initTILServerListeners(test);
    }

    public static <E extends EventWrapper<?>> void registerWrapperImpl(E wrapper) {
        CommonEventsAPI api = getEventsAPI(wrapper.isClient(),wrapper.isServer());
        if(Objects.nonNull(api)) api.register(wrapper);
    }
    
    public static <A> A setAABB(Box box) {
        return setAABB(false,false,box);
    }
    
    public static <A> A setAABB(EventType<?> type, Box box) {
        return setAABB(type.isClient(),type.isServer(),box);
    }
    
    public static <A> A setAABB(boolean client, Box box) {
        return setAABB(client,false,box);
    }
    
    public static <A> A setAABB(boolean client, boolean server, Box box) {
        return getEventsAPI(client,server).setAABB(box);
    }
    
    public static <A> A setActionResult(ActionResult result) {
        return setActionResult(false,false,result);
    }
    
    public static <A> A setActionResult(EventType<?> type, ActionResult result) {
        return setActionResult(type.isClient(),type.isServer(),result);
    }
    
    public static <A> A setActionResult(boolean client, ActionResult result) {
        return setActionResult(client,false,result);
    }
    
    public static <A> A setActionResult(boolean client, boolean server, ActionResult result) {
        return getEventsAPI(client,server).setActionResult(result);
    }
    
    public static <E> E setEventResult(Result result) {
        return setEventResult(false,false,result);
    }
    
    public static <E> E setEventResult(EventType<?> type, Result result) {
        return setEventResult(type.isClient(),type.isServer(),result);
    }
    
    public static <E> E setEventResult(boolean client, Result result) {
        return setEventResult(client,false,result);
    }
    
    public static <E> E setEventResult(boolean client, boolean server, Result result) {
        return getEventsAPI(client,server).setEventResult(result);
    }
    
    public static <D> @Nullable D setFacing(@Nullable Facing facing) {
        return setFacing(false,false,facing);
    }
    
    public static <D> @Nullable D setFacing(EventType<?> type, @Nullable Facing facing) {
        return setFacing(type.isClient(),type.isServer(),facing);
    }
    
    public static <D> @Nullable D setFacing(boolean client, @Nullable Facing facing) {
        return setFacing(client,false,facing);
    }
    
    public static <D> @Nullable D setFacing(boolean client, boolean server, @Nullable Facing facing) {
        return getEventsAPI(client,server).setFacing(facing);
    }
    
    public static <H> H setHand(Hand hand) {
        return setHand(false,false,hand);
    }
    
    public static <H> H setHand(EventType<?> type, Hand hand) {
        return setHand(type.isClient(),type.isServer(),hand);
    }
    
    public static <H> H setHand(boolean client, Hand hand) {
        return setHand(client,false,hand);
    }
    
    public static <H> H setHand(boolean client, boolean server, Hand hand) {
        return getEventsAPI(client,server).setHand(hand);
    }
    
    public static <V> V setVec3d(Vector3 vector) {
        return setVec3d(false,false,vector);
    }
    
    public static <V> V setVec3d(EventType<?> type, Vector3 vector) {
        return setVec3d(type.isClient(),type.isServer(),vector);
    }
    
    public static <V> V setVec3d(boolean client, Vector3 vector) {
        return setVec3d(client,false,vector);
    }
    
    public static <V> V setVec3d(boolean client, boolean server, Vector3 vector) {
        return getEventsAPI(client,server).setVec3d(vector);
    }
}