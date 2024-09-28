package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.util.CustomTick1_12_2;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.FAIL;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.SUCCESS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.OFFHAND;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public class ClientEvents1_12_2 implements ClientEventsAPI {

    private boolean defined;

    public ClientEvents1_12_2() {
        this.defined = false;
    }

    @Override public void defineEvents() {
        CAMERA_SETUP.setConnector(new CameraSetupEvent1_12_2());
        CLICK_INPUT.setConnector(new InputClickEvent1_12_2());
        CLIENT_CONNECTED.setConnector(new ClientConnectedEvent1_12_2());
        CLIENT_DISCONNECTED.setConnector(new ClientDisconnectedEvent1_12_2());
        CLIENT_RESPAWN.setConnector(new ClientRespawnEvent1_12_2());
        FOG_COLORS.setConnector(new FogColorsEvent1_12_2());
        FOG_DENSITY.setConnector(new FogDensityEvent1_12_2());
        FOG_RENDER.setConnector(new FogRenderEvent1_12_2());
        FOV_MODIFIER.setConnector(new FOVModifierEvent1_12_2());
        FOV_UPDATE.setConnector(new FOVUpdateEvent1_12_2());
        ITEM_TOOLTIP.setConnector(new ItemTooltipEvent1_12_2());
        KEY_INPUT.setConnector(new InputKeyEvent1_12_2());
        MOUSE_INPUT.setConnector(new InputMouseEvent1_12_2());
        MOUSE_RAW.setConnector(new RawMouseEvent1_12_2());
        MOUSE_SCROLL.setConnector(new MouseScrollEvent1_12_2());
        PLAYER_PUNCH_EMPTY.setConnector(new PlayerPunchEmptyEvent1_12_2());
        PLAYER_PUSH_OUT_OF_BLOCKS.setConnector(new PlayerPushOutOfBlocksEvent1_12_2());
        REGISTER_MODELS.setConnector(new RegisterModelsEvent1_12_2());
        RENDER_OVERLAY_BLOCK.setConnector(new RenderOverlayBlockEvent1_12_2());
        RENDER_OVERLAY_BOSS.setConnector(new RenderOverlayBossEvent1_12_2());
        RENDER_OVERLAY_CHAT.setConnector(new RenderOverlayChatEvent1_12_2());
        RENDER_OVERLAY_POST.setConnector(new RenderOverlayPostEvent1_12_2());
        RENDER_OVERLAY_PRE.setConnector(new RenderOverlayPreEvent1_12_2());
        RENDER_OVERLAY_TEXT.setConnector(new RenderOverlayTextEvent1_12_2());
        RENDER_WORLD_LAST.setConnector(new RenderWorldLastEvent1_12_2());
        SOUND_LOAD.setConnector(new LoadSoundEvent1_12_2());
        SOUND_PLAY.setConnector(new PlaySoundEvent1_12_2());
        SOUND_PLAY_SOURCE.setConnector(new PlaySoundSourceEvent1_12_2());
        SOUND_PLAY_STREAMING.setConnector(new PlayStreamingSoundSourceEvent1_12_2());
        SOUND_SETUP.setConnector(new SoundSetupEvent1_12_2());
        TICK_CLIENT.setConnector(new ClientTickEvent1_12_2());
        TICK_RENDER.setConnector(new RenderTickEvent1_12_2());
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
    
    @Override public <B> OverlayType getOverlayBlockType(B blockType) {
        switch((RenderBlockOverlayEvent.OverlayType)blockType) {
            case FIRE: return OverlayType.FIRE;
            case WATER: return OverlayType.WATER;
            default: return OverlayType.BLOCK;
        }
    }
    
    @Override public <E> OverlayType getOverlayElementType(E elementType) {
        switch((ElementType)elementType) {
            case AIR: return OverlayType.AIR;
            case ARMOR: return OverlayType.ARMOR;
            case BOSSHEALTH: return OverlayType.BOSSHEALTH;
            case BOSSINFO: return OverlayType.BOSSINFO;
            case CHAT: return OverlayType.CHAT;
            case CROSSHAIRS: return OverlayType.CROSSHAIRS;
            case DEBUG: return OverlayType.DEBUG;
            case EXPERIENCE: return OverlayType.EXPERIENCE;
            case FOOD: return OverlayType.FOOD;
            case FPS_GRAPH: return OverlayType.FPS_GRAPH;
            case HEALTH: return OverlayType.HEALTH;
            case HEALTHMOUNT: return OverlayType.HEALTHMOUNT;
            case HELMET: return OverlayType.HELMET;
            case HOTBAR: return OverlayType.HOTBAR;
            case JUMPBAR: return OverlayType.JUMPBAR;
            case PLAYER_LIST: return OverlayType.PLAYER_LIST;
            case PORTAL: return OverlayType.PORTAL;
            case POTION_ICONS: return OverlayType.POTION_ICONS;
            case SUBTITLES: return OverlayType.SUBTITLES;
            case TEXT: return OverlayType.TEXT;
            case VIGNETTE: return OverlayType.VIGNETTE;
            default: return OverlayType.ALL;
        }
    }
    
    @Override public <V> Vector3d getVec3d(V vector) {
        Vec3d vec = (Vec3d)vector;
        return new Vector3d(vec.x,vec.y,vec.z);
    }
    
    @Override public RenderContext initRenderer(Consumer<RenderContext> setters) {
        RenderContext ctx = RenderHelper.getContext();
        if(Objects.nonNull(ctx)) setters.accept(ctx);
        return ctx;
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
    
    @SuppressWarnings("unchecked") @Override public @Nullable EnumFacing setFacing(@Nullable Facing facing) {
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