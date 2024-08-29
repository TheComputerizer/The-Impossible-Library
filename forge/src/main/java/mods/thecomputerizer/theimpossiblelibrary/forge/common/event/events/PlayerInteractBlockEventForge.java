package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractBlockEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.UP;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_BLOCK;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.PASS;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand.MAINHAND;

public class PlayerInteractBlockEventForge extends PlayerInteractBlockEventWrapper<RightClickBlock> {
    
    @SubscribeEvent
    public static void onEvent(RightClickBlock event) {
        PLAYER_INTERACT_BLOCK.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected ItemStackAPI<?> getStackInHand() {
        return wrapItemStack(RightClickBlock::getItemStack);
    }
    
    @Override protected WorldAPI<?> getWorld() {
        return wrapWorld(RightClickBlock::getWorld);
    }
    
    @Override public void setEvent(RightClickBlock event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<RightClickBlock,Result> wrapBlockResultField() {
        return wrapGenericBoth(event -> EventHelper.getEventResult(event.getUseBlock()),
                (event,result) -> event.setUseBlock(EventHelper.setEventResult(result)),DEFAULT);
    }

    @Override protected EventFieldWrapper<RightClickBlock,ActionResult> wrapCancelResultField() {
        return wrapGenericBoth(event -> EventHelper.getActionResult(event.getCancellationResult()),
                (event,result) -> event.setCancellationResult(EventHelper.setActionResult(result)),PASS);
    }

    @Override protected EventFieldWrapper<RightClickBlock,Facing> wrapFacingField() {
        return wrapGenericGetter(event -> EventHelper.getFacing(event.getFace()),UP);
    }

    @Override protected EventFieldWrapper<RightClickBlock,Hand> wrapHandField() {
        return wrapGenericGetter(event -> EventHelper.getHand(event.getHand()),MAINHAND);
    }

    @Override protected EventFieldWrapper<RightClickBlock,Vector3d> wrapHitVecField() {
        return wrapGenericGetter(event -> EventHelper.getVec3d(event.getHitVec().getLocation()),VectorHelper.zero3D());
    }

    @Override protected EventFieldWrapper<RightClickBlock,Result> wrapItemResultField() {
        return wrapGenericBoth(event -> EventHelper.getEventResult(event.getUseItem()),
                (event,result) -> event.setUseItem(EventHelper.setEventResult(result)),DEFAULT);
    }

    @Override protected EventFieldWrapper<RightClickBlock,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(RightClickBlock::getPlayer);
    }

    @Override protected EventFieldWrapper<RightClickBlock,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(RightClickBlock::getPos);
    }
}