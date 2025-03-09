package mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.OFFHAND;
import static net.minecraft.world.InteractionHand.MAIN_HAND;
import static net.minecraft.world.InteractionHand.OFF_HAND;

public abstract class ClientEvents1_20 implements ClientEventsAPI {
    
    private boolean defined;
    
    protected ClientEvents1_20() {
        this.defined = false;
    }
    
    @Override public void defineEvents() {
        this.defined = true;
    }
    
    @Override public <A> Box getAABB(A aabb) {
        AABB box = (AABB)aabb;
        return ShapeHelper.box(box.minX,box.minY,box.minZ,box.maxX,box.maxY,box.maxZ);
    }
    
    @Override public <A> ActionResult getActionResult(A result) {
        return switch((InteractionResult)result) {
            case CONSUME -> ActionResult.CONSUME;
            case PASS -> ActionResult.PASS;
            case SUCCESS -> ActionResult.SUCCESS;
            default -> ActionResult.FAIL;
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
            default -> Facing.WEST;
        };
    }
    
    @Override public <H> Hand getHand(H hand) {
        return hand==MAIN_HAND ? MAINHAND : OFFHAND;
    }
    
    @Override public <B> OverlayType getOverlayBlockType(B blockType) {
        return switch((RenderBlockScreenEffectEvent.OverlayType)blockType) {
            case FIRE -> OverlayType.FIRE;
            case WATER -> OverlayType.WATER;
            default -> OverlayType.BLOCK;
        };
    }
    
    @Override public <E> OverlayType getOverlayElementType(E elementType) {
        return switch(((VanillaGuiOverlay)elementType)) {
            case AIR_LEVEL -> OverlayType.AIR;
            case ARMOR_LEVEL -> OverlayType.ARMOR;
            case BOSS_EVENT_PROGRESS -> OverlayType.BOSSINFO;
            case CHAT_PANEL -> OverlayType.CHAT;
            case CROSSHAIR -> OverlayType.CROSSHAIRS;
            case DEBUG_TEXT -> OverlayType.DEBUG;
            case EXPERIENCE_BAR -> OverlayType.EXPERIENCE;
            case FOOD_LEVEL -> OverlayType.FOOD;
            case HELMET -> OverlayType.HELMET;
            case HOTBAR -> OverlayType.HOTBAR;
            case JUMP_BAR -> OverlayType.JUMPBAR;
            case PLAYER_HEALTH -> OverlayType.HEALTH;
            case PLAYER_LIST -> OverlayType.PLAYER_LIST;
            case PORTAL -> OverlayType.PORTAL;
            case POTION_ICONS -> OverlayType.POTION_ICONS;
            case SUBTITLES -> OverlayType.SUBTITLES;
            case TITLE_TEXT -> OverlayType.TEXT;
            case VIGNETTE -> OverlayType.VIGNETTE;
            default -> OverlayType.ALL;
        };
    }
    
    @Override public <V> Vector3 getVec3d(V vector) {
        Vec3 vec = (Vec3)vector;
        return new Vector3(vec.x,vec.y,vec.z);
    }
    
    @Override public RenderContext initRenderer(Consumer<RenderContext> setters) {
        RenderContext ctx = RenderHelper.getContext();
        if(Objects.nonNull(ctx)) setters.accept(ctx);
        return ctx;
    }
    
    @Override public boolean isDefined() {
        return this.defined;
    }
    
    @SuppressWarnings("unchecked")
    @Override public <B> B setAABB(Box box) {
        return (B)new AABB(box.minX(),box.minY(),box.minZ(),box.maxX(),box.maxY(),box.maxZ());
    }
    
    @SuppressWarnings("unchecked")
    @Override public <A> A setActionResult(ActionResult result) {
        return switch(result) {
            case CONSUME -> (A)InteractionResult.CONSUME;
            case PASS -> (A)InteractionResult.PASS;
            case SUCCESS -> (A)InteractionResult.SUCCESS;
            default -> (A)InteractionResult.FAIL;
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
            default -> (F)Direction.WEST;
        };
    }
    
    @SuppressWarnings("unchecked")
    @Override public <H> H setHand(Hand hand) {
        return (H)(hand==MAINHAND ? MAIN_HAND : OFF_HAND);
    }
    
    @SuppressWarnings("unchecked")
    @Override public <V> V setVec3d(Vector3 vector) {
        return (V)new Vec3(vector.dX(),vector.dY(),vector.dZ());
    }
}
