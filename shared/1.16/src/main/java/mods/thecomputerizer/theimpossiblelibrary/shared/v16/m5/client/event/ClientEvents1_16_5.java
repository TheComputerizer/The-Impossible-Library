package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.event;

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
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.OFFHAND;
import static net.minecraft.world.InteractionHand.MAIN_HAND;
import static net.minecraft.world.InteractionHand.OFF_HAND;

public abstract class ClientEvents1_16_5 implements ClientEventsAPI {
    
    private boolean defined;
    
    protected ClientEvents1_16_5() {
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
        return hand==MAIN_HAND ? MAINHAND : OFFHAND;
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
    
    @Override public <V> Vector3 getVec3d(V vector) {
        com.mojang.math.Vector3d vec = (com.mojang.math.Vector3d)vector;
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
        switch(result) {
            case CONSUME: return (A)ActionResultType.CONSUME;
            case PASS: return (A)ActionResultType.PASS;
            case SUCCESS: return (A)ActionResultType.SUCCESS;
            default: return (A)ActionResultType.FAIL;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override public <F> @Nullable F setFacing(@Nullable Facing facing) {
        if(Objects.isNull(facing)) return null;
        switch(facing) {
            case DOWN: return (F)Direction.DOWN;
            case EAST: return (F)Direction.EAST;
            case NORTH: return (F)Direction.NORTH;
            case SOUTH: return (F)Direction.SOUTH;
            case UP: return (F)Direction.UP;
            default: return (F)Direction.WEST;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override public <H> H setHand(Hand hand) {
        return (H)(hand==MAINHAND ? MAIN_HAND : OFF_HAND);
    }
    
    @SuppressWarnings("unchecked")
    @Override public <V> V setVec3d(Vector3 vector) {
        return (V)new com.mojang.math.Vector3d(vector.dX(),vector.dY(),vector.dZ());
    }
}
